package br.com.ricardo.gestao_vagas.modules.company.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.ricardo.gestao_vagas.modules.company.CompanyRepository;
import br.com.ricardo.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.ricardo.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.ricardo.gestao_vagas.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {
        private MockMvc mockMvc;

        @Autowired
        private WebApplicationContext webApplicationContext;

        @Autowired
        private CompanyRepository companyRepository;

        @Before
        public void setup() {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
        }

        @Test
        @DisplayName("Should be able to create a job")
        public void should_be_able_to_create_a_job() throws Exception {
                var company = CompanyEntity.builder()
                                .username("USERNAME_TEST")
                                .password("12345678")
                                .email("email_test@mail.com")
                                .description("DESCRIPTION_TEST")
                                .website("WEBSITE_TEST")
                                .name("NAME_TEST")
                                .build();

                company = companyRepository.saveAndFlush(company);

                var createJobDTO = CreateJobDTO.builder()
                                .benefits("BENEFITS_TEST")
                                .description("DESCRIPTION_TEST")
                                .level("LEVEL_TEST")
                                .build();

                try {
                        var result = mockMvc.perform(
                                        MockMvcRequestBuilders.post("/job")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(TestUtils.asJsonString(createJobDTO))
                                                        .header("Authorization",
                                                                        "Bearer " + TestUtils.generateToken(
                                                                                        company.getId(),
                                                                                        "JAVAGAS_@123#")))
                                        .andExpect(status().isOk());

                        System.out.println(result);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                }
        }

        @Test
        @DisplayName("Should not be able to create a job with company not found")
        public void should_not_be_able_to_create_a_job_with_company_not_found() throws Exception {
                var createJobDTO = CreateJobDTO.builder()
                                .benefits("BENEFITS_TEST")
                                .description("DESCRIPTION_TEST")
                                .level("LEVEL_TEST")
                                .build();

                mockMvc.perform(
                                MockMvcRequestBuilders.post("/job")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(TestUtils.asJsonString(createJobDTO))
                                                .header("Authorization",
                                                                "Bearer " + TestUtils.generateToken(
                                                                                UUID.randomUUID(),
                                                                                "JAVAGAS_@123#")))
                                .andExpect(status().isBadRequest());
        }
}
