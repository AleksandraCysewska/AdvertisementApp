package com.cysewska.advertisement.web.rest;

import com.cysewska.advertisement.AdvertisementApp;

import com.cysewska.advertisement.domain.Advertisement;
import com.cysewska.advertisement.repository.AdvertisementRepository;
import com.cysewska.advertisement.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cysewska.advertisement.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cysewska.advertisement.domain.enumeration.ECategory;
import com.cysewska.advertisement.domain.enumeration.ECollege;
import com.cysewska.advertisement.domain.enumeration.EDepartment;
/**
 * Test class for the AdvertisementResource REST controller.
 *
 * @see AdvertisementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdvertisementApp.class)
public class AdvertisementResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final ECategory DEFAULT_CATEGORY = ECategory.WYNIKI;
    private static final ECategory UPDATED_CATEGORY = ECategory.KONKURS;

    private static final ECollege DEFAULT_COLLEGE = ECollege.KOSZALIN;
    private static final ECollege UPDATED_COLLEGE = ECollege.SZCZECIN;

    private static final EDepartment DEFAULT_DEPARTMENT = EDepartment.WNE;
    private static final EDepartment UPDATED_DEPARTMENT = EDepartment.WEII;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdvertisementMockMvc;

    private Advertisement advertisement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdvertisementResource advertisementResource = new AdvertisementResource(advertisementRepository);
        this.restAdvertisementMockMvc = MockMvcBuilders.standaloneSetup(advertisementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Advertisement createEntity(EntityManager em) {
        Advertisement advertisement = new Advertisement()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .author(DEFAULT_AUTHOR)
            .category(DEFAULT_CATEGORY)
            .college(DEFAULT_COLLEGE)
            .department(DEFAULT_DEPARTMENT)
            .email(DEFAULT_EMAIL)
            .link(DEFAULT_LINK)
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE);
        return advertisement;
    }

    @Before
    public void initTest() {
        advertisement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdvertisement() throws Exception {
        int databaseSizeBeforeCreate = advertisementRepository.findAll().size();

        // Create the Advertisement
        restAdvertisementMockMvc.perform(post("/api/advertisements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advertisement)))
            .andExpect(status().isCreated());

        // Validate the Advertisement in the database
        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeCreate + 1);
        Advertisement testAdvertisement = advertisementList.get(advertisementList.size() - 1);
        assertThat(testAdvertisement.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAdvertisement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAdvertisement.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAdvertisement.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testAdvertisement.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testAdvertisement.getCollege()).isEqualTo(DEFAULT_COLLEGE);
        assertThat(testAdvertisement.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testAdvertisement.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAdvertisement.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testAdvertisement.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testAdvertisement.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAdvertisementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = advertisementRepository.findAll().size();

        // Create the Advertisement with an existing ID
        advertisement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdvertisementMockMvc.perform(post("/api/advertisements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advertisement)))
            .andExpect(status().isBadRequest());

        // Validate the Advertisement in the database
        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAuthorIsRequired() throws Exception {
        int databaseSizeBeforeTest = advertisementRepository.findAll().size();
        // set the field null
        advertisement.setAuthor(null);

        // Create the Advertisement, which fails.

        restAdvertisementMockMvc.perform(post("/api/advertisements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advertisement)))
            .andExpect(status().isBadRequest());

        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCollegeIsRequired() throws Exception {
        int databaseSizeBeforeTest = advertisementRepository.findAll().size();
        // set the field null
        advertisement.setCollege(null);

        // Create the Advertisement, which fails.

        restAdvertisementMockMvc.perform(post("/api/advertisements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advertisement)))
            .andExpect(status().isBadRequest());

        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = advertisementRepository.findAll().size();
        // set the field null
        advertisement.setEmail(null);

        // Create the Advertisement, which fails.

        restAdvertisementMockMvc.perform(post("/api/advertisements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advertisement)))
            .andExpect(status().isBadRequest());

        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdvertisements() throws Exception {
        // Initialize the database
        advertisementRepository.saveAndFlush(advertisement);

        // Get all the advertisementList
        restAdvertisementMockMvc.perform(get("/api/advertisements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(advertisement.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].college").value(hasItem(DEFAULT_COLLEGE.toString())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))));
    }

    @Test
    @Transactional
    public void getAdvertisement() throws Exception {
        // Initialize the database
        advertisementRepository.saveAndFlush(advertisement);

        // Get the advertisement
        restAdvertisementMockMvc.perform(get("/api/advertisements/{id}", advertisement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(advertisement.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.college").value(DEFAULT_COLLEGE.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK.toString()))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64Utils.encodeToString(DEFAULT_PICTURE)));
    }

    @Test
    @Transactional
    public void getNonExistingAdvertisement() throws Exception {
        // Get the advertisement
        restAdvertisementMockMvc.perform(get("/api/advertisements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdvertisement() throws Exception {
        // Initialize the database
        advertisementRepository.saveAndFlush(advertisement);
        int databaseSizeBeforeUpdate = advertisementRepository.findAll().size();

        // Update the advertisement
        Advertisement updatedAdvertisement = advertisementRepository.findOne(advertisement.getId());
        // Disconnect from session so that the updates on updatedAdvertisement are not directly saved in db
        em.detach(updatedAdvertisement);
        updatedAdvertisement
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .author(UPDATED_AUTHOR)
            .category(UPDATED_CATEGORY)
            .college(UPDATED_COLLEGE)
            .department(UPDATED_DEPARTMENT)
            .email(UPDATED_EMAIL)
            .link(UPDATED_LINK)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE);

        restAdvertisementMockMvc.perform(put("/api/advertisements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdvertisement)))
            .andExpect(status().isOk());

        // Validate the Advertisement in the database
        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeUpdate);
        Advertisement testAdvertisement = advertisementList.get(advertisementList.size() - 1);
        assertThat(testAdvertisement.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAdvertisement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdvertisement.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAdvertisement.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testAdvertisement.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testAdvertisement.getCollege()).isEqualTo(UPDATED_COLLEGE);
        assertThat(testAdvertisement.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testAdvertisement.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAdvertisement.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testAdvertisement.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testAdvertisement.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdvertisement() throws Exception {
        int databaseSizeBeforeUpdate = advertisementRepository.findAll().size();

        // Create the Advertisement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdvertisementMockMvc.perform(put("/api/advertisements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(advertisement)))
            .andExpect(status().isCreated());

        // Validate the Advertisement in the database
        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdvertisement() throws Exception {
        // Initialize the database
        advertisementRepository.saveAndFlush(advertisement);
        int databaseSizeBeforeDelete = advertisementRepository.findAll().size();

        // Get the advertisement
        restAdvertisementMockMvc.perform(delete("/api/advertisements/{id}", advertisement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Advertisement> advertisementList = advertisementRepository.findAll();
        assertThat(advertisementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Advertisement.class);
        Advertisement advertisement1 = new Advertisement();
        advertisement1.setId(1L);
        Advertisement advertisement2 = new Advertisement();
        advertisement2.setId(advertisement1.getId());
        assertThat(advertisement1).isEqualTo(advertisement2);
        advertisement2.setId(2L);
        assertThat(advertisement1).isNotEqualTo(advertisement2);
        advertisement1.setId(null);
        assertThat(advertisement1).isNotEqualTo(advertisement2);
    }
}
