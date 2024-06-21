package ar.edu.unq.desapp.grupoD.backenddesappapi.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(packages = "ar.edu.unq.desapp.grupod")
public class AnnotationTest {

    @ArchTest
    public static void servicesShouldBeAnnotatedWithService(JavaClasses importedClasses) {
        ArchRuleDefinition.classes()
                .that().resideInAPackage("..services..")
                .and().haveSimpleNameNotEndingWith("Test")
                .should().beAnnotatedWith(Service.class)
                .check(importedClasses);
    }

    @ArchTest
    public static void repositoriesShouldBeAnnotatedWithRepository(JavaClasses importedClasses) {
        ArchRuleDefinition.classes()
                .that().resideInAPackage("..repositories..")
                .and().haveSimpleNameNotEndingWith("Test")
                .should().beAnnotatedWith(Repository.class)
                .check(importedClasses);
    }

    @ArchTest
    public static void controllersShouldBeAnnotatedWithControllerOrRestController(JavaClasses importedClasses) {
        ArchRuleDefinition.classes()
                .that().resideInAPackage("..webservice..")
                .and().haveSimpleNameNotEndingWith("Test")
                .should().beAnnotatedWith(Controller.class)
                .orShould().beAnnotatedWith(RestController.class)
                .check(importedClasses);
    }
}