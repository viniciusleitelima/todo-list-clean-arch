package com.example.todo_list_clean_arch;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;


import com.tngtech.archunit.core.domain.JavaClasses;

import com.tngtech.archunit.core.importer.ClassFileImporter;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

@DisplayName("Validação Manual das Regras ArchUnit")

public class ArchUnitValidationTest {

    private static JavaClasses classes;

    @BeforeAll

    static void setUp() {

        // Importa todas as classes do projeto

        classes = new ClassFileImporter().importPackages("com.example.todo_list_clean_arch");

        System.out.println("=== CLASSES IMPORTADAS ===");

        System.out.println("Total de classes: " + classes.size());

        classes.forEach(clazz -> System.out.println("- " + clazz.getName()));

        System.out.println("==========================");

    }

    @Test

    @ArchTest
    @DisplayName("Validar que Domain não depende de outras camadas")

    void validateDomainIsolation() {

        System.out.println("\n🔍 VALIDANDO ISOLAMENTO DO DOMAIN...");

        ArchRule rule = noClasses()

                .that().resideInAPackage("..domain..")

                .should().dependOnClassesThat().resideInAnyPackage("..adapter..", "..infra..");

        try {

            rule.check(classes);

            System.out.println("✅ SUCESSO: Domain está isolado corretamente!");

        } catch (AssertionError e) {

            System.out.println("❌ FALHA: Domain não está isolado!");

            System.out.println("Detalhes: " + e.getMessage());

            throw e;

        }

    }

    @Test

    @DisplayName("Validar localização dos Controllers")

    void validateControllersLocation() {

        System.out.println("\n�� VALIDANDO LOCALIZAÇÃO DOS CONTROLLERS...");

        ArchRule rule = classes()

                .that().areAnnotatedWith("org.springframework.web.bind.annotation.RestController")

                .should().resideInAPackage("..adapter..");

        try {

            rule.check(classes);

            System.out.println("✅ SUCESSO: Controllers estão no local correto!");

        } catch (AssertionError e) {

            System.out.println("❌ FALHA: Controllers não estão no local correto!");

            System.out.println("Detalhes: " + e.getMessage());

            throw e;

        }

    }

    @Test

    @DisplayName("Validar localização dos DTOs")

    void validateDTOsLocation() {

        System.out.println("\n🔍 VALIDANDO LOCALIZAÇÃO DOS DTOs...");

        ArchRule rule = classes()

                .that().haveSimpleNameEndingWith("DTO")

                .should().resideInAPackage("..infra..");

        try {

            rule.check(classes);

            System.out.println("✅ SUCESSO: DTOs estão no local correto!");

        } catch (AssertionError e) {

            System.out.println("❌ FALHA: DTOs não estão no local correto!");

            System.out.println("Detalhes: " + e.getMessage());

            throw e;

        }

    }

    @Test

    @DisplayName("Validar localização dos Mappers")

    void validateMappersLocation() {

        System.out.println("\n🔍 VALIDANDO LOCALIZAÇÃO DOS MAPPERS...");

        ArchRule rule = classes()

                .that().haveSimpleNameEndingWith("Mapper")

                .should().resideInAPackage("..infra..");

        try {

            rule.check(classes);

            System.out.println("✅ SUCESSO: Mappers estão no local correto!");

        } catch (AssertionError e) {

            System.out.println("❌ FALHA: Mappers não estão no local correto!");

            System.out.println("Detalhes: " + e.getMessage());

            throw e;

        }

    }

    @Test

    @DisplayName("Validar localização das Entities")

    void validateEntitiesLocation() {

        System.out.println("\n🔍 VALIDANDO LOCALIZAÇÃO DAS ENTITIES...");

        ArchRule rule = classes()

                .that().haveSimpleNameEndingWith("Entity")

                .should().resideInAPackage("..infra..");

        try {

            rule.check(classes);

            System.out.println("✅ SUCESSO: Entities estão no local correto!");

        } catch (AssertionError e) {

            System.out.println("❌ FALHA: Entities não estão no local correto!");

            System.out.println("Detalhes: " + e.getMessage());

            throw e;

        }

    }

    @Test

    @DisplayName("Validar que Domain não tem anotações Spring")

    void validateDomainNoSpringAnnotations() {

        System.out.println("\n🔍 VALIDANDO QUE DOMAIN NÃO TEM ANOTAÇÕES SPRING...");

        ArchRule rule = noClasses()

                .that().resideInAPackage("..domain..")

                .should().beAnnotatedWith("org.springframework.stereotype.Component")

                .orShould().beAnnotatedWith("org.springframework.stereotype.Service")

                .orShould().beAnnotatedWith("org.springframework.stereotype.Repository");

        try {

            rule.check(classes);

            System.out.println("✅ SUCESSO: Domain não tem anotações Spring!");

        } catch (AssertionError e) {

            System.out.println("❌ FALHA: Domain tem anotações Spring!");

            System.out.println("Detalhes: " + e.getMessage());

            throw e;

        }

    }

    @Test

    @DisplayName("Validar arquitetura em camadas")

    void validateLayeredArchitecture() {

        System.out.println("\n�� VALIDANDO ARQUITETURA EM CAMADAS...");

        ArchRule rule = com.tngtech.archunit.library.Architectures.layeredArchitecture()
                .layer("Domain").definedBy("..domain..")
                .layer("Adapter").definedBy("..adapter..")
                .layer("Infrastructure").definedBy("..infra..")

                .whereLayer("Domain").mayNotBeAccessedByAnyLayer()

                .whereLayer("Adapter").mayOnlyBeAccessedByLayers("Infrastructure")

                .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer();

        try {

            rule.check(classes);

            System.out.println("✅ SUCESSO: Arquitetura em camadas está correta!");

        } catch (AssertionError e) {

            System.out.println("❌ FALHA: Arquitetura em camadas está incorreta!");

            System.out.println("Detalhes: " + e.getMessage());

            throw e;

        }

    }

    @Test

    @DisplayName("Relatório completo de validação")

    void completeValidationReport() {

        System.out.println("\n" + "=".repeat(50));

        System.out.println("📊 RELATÓRIO COMPLETO DE VALIDAÇÃO");

        System.out.println("=".repeat(50));

        // Lista todas as classes por pacote

        System.out.println("\n📁 CLASSES POR PACOTE:");

        classes.stream()

                .collect(java.util.stream.Collectors.groupingBy(

                        clazz -> clazz.getPackageName(),

                        java.util.stream.Collectors.toList()

                ))

                .forEach((packageName, classList) -> {

                    System.out.println("\n�� " + packageName + ":");

                    classList.forEach(clazz ->

                            System.out.println("  - " + clazz.getSimpleName())

                    );

                });

        System.out.println("\n" + "=".repeat(50));

        System.out.println("✅ VALIDAÇÃO COMPLETA FINALIZADA!");

        System.out.println("=".repeat(50));

    }

}
