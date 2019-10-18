package tsystems.javaschool.eCare.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Context
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{HibernateConfig.class, WebSecurityConfig.class};
    }

    // Web-Configuration
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    // Address Registration
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    // Устанока кодировки UTF-8
    protected CharacterEncodingFilter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new CharacterEncodingFilter[] {characterEncodingFilter};
    }
}
