package com.richardroj.mower;

import com.richardroj.mower.converter.MowerConverter;
import com.richardroj.mower.service.IMowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class MowerApplication implements CommandLineRunner {
    @Autowired
    IMowerService mowerService;
    @Autowired
    MowerConverter mowerConverter;

    private static Logger logger = LoggerFactory
        .getLogger(MowerApplication.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("EXECUTING : command line mower app");
        if (args.length > 0) {
            Path path = Paths.get(args[0]);

            List<String> lines = Files.readAllLines(path);
            var mowersMap = mowerConverter.convertLinesToMowersMap(lines);
            mowersMap.forEach((mower, commands) -> {
                mowerService.move(mower, commands);
            });
        }
    }



    public static void main(String[] args) {
		SpringApplication.run(MowerApplication.class, args);
	}

}
