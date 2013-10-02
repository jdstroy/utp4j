package ch.uzh.csg.utp4j.examples.configtest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import ch.uzh.csg.utp4j.channels.UtpServerSocketChannel;
import ch.uzh.csg.utp4j.channels.UtpSocketChannel;
import ch.uzh.csg.utp4j.channels.futures.UtpAcceptFuture;
import ch.uzh.csg.utp4j.channels.futures.UtpReadFuture;
import ch.uzh.csg.utp4j.examples.SaveFileListener;

public class ConfigTestRead {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		ByteBuffer buffer = ByteBuffer.allocate(150000000);
		while(true) {
			UtpServerSocketChannel server = UtpServerSocketChannel.open();
			server.bind(new InetSocketAddress(13344));
			UtpAcceptFuture acceptFuture = server.accept();
			acceptFuture.block();
			UtpSocketChannel channel = acceptFuture.getChannel();
			UtpReadFuture readFuture = channel.read(buffer);
			readFuture.setListener(null);
			readFuture.block();
			System.out.println("reading end");
			channel.close();
			server.close();
			server = null;
			channel = null;
			buffer.clear();
			readFuture = null;
			acceptFuture = null;
			Thread.sleep(5000);
		}

	}

}