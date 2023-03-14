package com.api.backend1;

import com.api.backend1.controllers.CultureController;
import com.api.backend1.dtos.CultureDto;
import com.api.backend1.models.CultureModel;
import com.api.backend1.services.CultureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CultureController.class)
public class CultureControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private CultureService cultureService;

	@InjectMocks
	private CultureController cultureController;

	private List<CultureDto> cultureList;

	@BeforeEach
	void setUp() {
		cultureList = new ArrayList<>();

		CultureDto culture1 = new CultureDto();
		culture1.setName("Culture 1");
		cultureList.add(culture1);

		CultureDto culture2 = new CultureDto();
		culture2.setName("Culture 2");
		cultureList.add(culture2);
	}

	@Test
	void testGetAllCulture() throws Exception {
		when(cultureService.findAll()).thenReturn(cultureList.stream().map(cultureDto -> {
			CultureModel cultureModel = new CultureModel();
			cultureModel.setName(cultureDto.getName());
			return cultureModel;
		}).collect(Collectors.toList()));


		mockMvc.perform(MockMvcRequestBuilders.get("/culture")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Culture 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Culture 2"));
	}


}
