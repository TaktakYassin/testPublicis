package com.example.testPublicis;

import com.example.testPublicis.exception.InvalidFileContentException;
import com.example.testPublicis.service.MowerPositionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPublicisApplicationTests {

	@Autowired
	private MowerPositionServiceImpl mowerPositionService;

	@Test
	public void whenDataIsCorrect_expectResult() throws IOException, InvalidFileContentException {
		List<String> content = Files.readAllLines(Paths.get("src/test/resources/data_correct.txt"));
		List<String> result = mowerPositionService.calculatePositions(content);
		List<String> expected = Arrays.asList("1 3 N","5 1 E");
		assertEquals(expected, result);
	}

	@Test(expected = InvalidFileContentException.class)
	public void whenFileIsEmpty_throwInvalidFileContentException() throws IOException, InvalidFileContentException {
		List<String> content = Files.readAllLines(Paths.get("src/test/resources/empty_file.txt"));
		mowerPositionService.calculatePositions(content);
	}


	@Test(expected = InvalidFileContentException.class)
	public void whenDataIsMissing_throwInvalidFileContentException() throws IOException, InvalidFileContentException {
		List<String> content = Files.readAllLines(Paths.get("src/test/resources/map_size_missing.txt"));
		mowerPositionService.calculatePositions(content);
	}

	@Test
	public void whenMowIsBlocking_expectResult() throws IOException, InvalidFileContentException {
		List<String> content = Files.readAllLines(Paths.get("src/test/resources/mow_blocking_position.txt"));
		List<String> result = mowerPositionService.calculatePositions(content);
		List<String> expected = Arrays.asList("1 3 N","5 1 E","4 1 E","1 2 N");
		assertEquals(expected, result);
	}



}
