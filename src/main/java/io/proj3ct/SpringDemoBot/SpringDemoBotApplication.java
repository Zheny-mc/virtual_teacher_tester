package io.proj3ct.SpringDemoBot;

import io.proj3ct.SpringDemoBot.clients.telegram_client.TelegramClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDemoBotApplication /*implements CommandLineRunner*/ {
//	final TelegramClient client;
//
//	public SpringDemoBotApplication(TelegramClient client) {
//		this.client = client;
//	}
//
//	void testGetStructCourse() {
//		System.out.println(client.getStructCourse("fdggfdg"));
//	}
//
//	void testGetTest() {
//		System.out.println(client.getTest("1_1"));
//	}
//
//	void testGetStatusTest() {
//		System.out.println(client.getStatusTest());
//	}
//
//	void contextLoads() {
//		client.updateStatusTest(false);
//	}
	public static void main(String[] args) {
		SpringApplication.run(SpringDemoBotApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		testGetStructCourse();
//		testGetTest();
//		testGetStatusTest();
//		contextLoads();
//	}
}
