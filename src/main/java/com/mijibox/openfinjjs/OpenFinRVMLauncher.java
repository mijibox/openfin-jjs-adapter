package com.mijibox.openfinjjs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.json.Json;
import javax.json.JsonReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

public class OpenFinRVMLauncher {

	private final static Logger logger = LoggerFactory.getLogger(OpenFinRVMLauncher.class);
	private String rvmVersion;
	private String assetsUrl;
	private String rvmExecutableFolder;
	private String connetionUUID;

	public OpenFinRVMLauncher() {
		this("https://cdn.openfin.co", null);
	}

	public OpenFinRVMLauncher(String rvmVersion) {
		this("https://cdn.openfin.co", rvmVersion);
	}

	public OpenFinRVMLauncher(String assetsUrl, String rvmVersion) {
		this.rvmExecutableFolder = ".";
		this.assetsUrl = assetsUrl;
		this.rvmVersion = rvmVersion == null ? this.getLatestVersion() : rvmVersion;
		this.connetionUUID = UUID.randomUUID().toString();
	}

	private String getLatestVersion() {
		String v = null;
		try {
			String latestVersionUrl = this.assetsUrl + "/release/rvm/latestVersion";
			logger.info("retrieving RVM latest version number from: {}", latestVersionUrl);
			URL url = new URL(latestVersionUrl);
			BufferedInputStream bis = new BufferedInputStream(url.openStream());
			v = new String(bis.readAllBytes());
			logger.info("Got RVM latestVersion: {}", v);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		return v;
	}

	private Path getRVMExecutablePath() {
		return Paths.get("./rvm", this.rvmVersion, "OpenFinRVM.exe");
	}

	private void download() {
		try {
			URL url = new URL(this.assetsUrl + "/release/rvm/" + this.rvmVersion);
			ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
			Path tempFile = Files.createTempFile(null, null);
			FileOutputStream fileOutputStream = new FileOutputStream(tempFile.toFile());
			long size = fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
			logger.info("RVM downloaded, version: {}, path: {}, size: {}", this.rvmVersion, tempFile, size);
			fileOutputStream.close();
			this.extract(tempFile);
			Files.delete(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	private void extract(Path zipFile) {
		try {
			this.unzip(zipFile, "./rvm/" + this.rvmVersion);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}

	public void unzip(Path zipFile, String destDirectory) throws IOException {
		Path targetFolder = Paths.get(destDirectory);
		Files.createDirectories(targetFolder);
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile.toFile()));
		ZipEntry entry = zipIn.getNextEntry();
		// iterates over entries in the zip file
		while (entry != null) {
			Path filePath = Paths.get(destDirectory, entry.getName());
			if (!entry.isDirectory()) {
				// if the entry is a file, extracts it
				extractFile(zipIn, filePath);
			} else {
				// if the entry is a directory, make the directory
				Files.createDirectory(filePath);
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
	}

	/**
	 * Extracts a zip entry (file entry)
	 * 
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private void extractFile(ZipInputStream zipIn, Path filePath) throws IOException {
		ReadableByteChannel readableByteChannel = Channels.newChannel(zipIn);
		FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile());
		long size = fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
		logger.info("File extracted, path: {}, size: {}", filePath, size);
		fileOutputStream.close();
	}

	private Path createStartupConfig() throws IOException {
		var jsonRuntime = Json.createObjectBuilder();
		jsonRuntime.add("version", "stable");
		jsonRuntime.add("arguments", "--v=1 --runtime-information-channel-v6=" + this.connetionUUID);
		
		var startUpAppConfig = Json.createObjectBuilder()
				.add("uuid", "default_" + this.connetionUUID)
				.add("name", "default_" + this.connetionUUID)
				.add("url", "https://www.google.com")
				.add("autoShow", true).build();

		var jsonConfig = Json.createObjectBuilder();
		jsonConfig.add("runtime", jsonRuntime);
//		jsonConfig.add("startup_app", startUpAppConfig);

		Path config = Paths.get("adapter.json");

		Files.write(config, jsonConfig.build().toString().getBytes(), StandardOpenOption.CREATE,
				StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

		return config;
	}

	public CompletionStage<OpenFinConnection> launch() {
		logger.info("launching OpenFinRVM, version: {}", this.rvmVersion);

		// create named pipe, get port number to connect to.
		CompletableFuture<Integer> portNumberFuture = CompletableFuture.supplyAsync(() -> {
			String pipeName = "\\\\.\\pipe\\chrome." + this.connetionUUID;
			logger.info("creating named pipe: {}", pipeName);
			HANDLE hNamedPipe = Kernel32.INSTANCE.CreateNamedPipe(pipeName, WinBase.PIPE_ACCESS_DUPLEX, // dwOpenMode
					WinBase.PIPE_TYPE_BYTE | WinBase.PIPE_READMODE_BYTE | WinBase.PIPE_WAIT, // dwPipeMode
					1, // nMaxInstances,
					Byte.MAX_VALUE, // nOutBufferSize,
					Byte.MAX_VALUE, // nInBufferSize,
					1000, // nDefaultTimeOut,
					null); // lpSecurityAttributes

			if (Kernel32.INSTANCE.ConnectNamedPipe(hNamedPipe, null)) {
				logger.info("connected to named pipe {}", pipeName);
			}

			byte[] buffer = new byte[4096];
			IntByReference lpNumberOfBytesRead = new IntByReference(0);
			Kernel32.INSTANCE.ReadFile(hNamedPipe, buffer, buffer.length, lpNumberOfBytesRead, null);

			ByteBuffer bb = ByteBuffer.wrap(buffer);
			bb.putInt(20, Kernel32.INSTANCE.GetCurrentProcessId());
			IntByReference lpNumberOfBytesWrite = new IntByReference(0);
			Kernel32.INSTANCE.WriteFile(hNamedPipe, buffer, buffer.length, lpNumberOfBytesWrite, null);

			Kernel32.INSTANCE.ReadFile(hNamedPipe, buffer, buffer.length, lpNumberOfBytesRead, null);

			logger.info("read port info from named pipe, size:{}", lpNumberOfBytesRead.getValue());
			String portInfoJson = new String(buffer, 24, lpNumberOfBytesRead.getValue() - 24);
			logger.info("portInfo: {}", portInfoJson);

			JsonReader jsonReader = Json.createReader(new StringReader(portInfoJson));
			int port = jsonReader.readObject().getJsonObject("payload").getInt("port");
			logger.info("port number: {}", port);

			return port;
		});

		return CompletableFuture.supplyAsync(() -> {
			try {
				var configPath = this.createStartupConfig();

				Path rvmPath = this.getRVMExecutablePath();
				if (!Files.exists(rvmPath, LinkOption.NOFOLLOW_LINKS)) {
					logger.debug("target rvm version doesn't exist.");
					this.download();
				}
				ProcessBuilder pb = new ProcessBuilder(rvmPath.toAbsolutePath().normalize().toString(),
						"--disable-auto-updates", "--disable-self-install",
						"--config=" + configPath.toAbsolutePath().normalize().toUri());

				pb.start();
				return pb;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;

		}).thenCombine(portNumberFuture, (v, port) -> {
			return new OpenFinConnection(connetionUUID, port);
		});
	}
}
