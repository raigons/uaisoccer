package com.thoughtworks.uaisoccer.teams;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TeamKeyGeneratorTest {

    private TeamKeyGenerator keyGenerator = new TeamKeyGenerator();

    @Test
    public void shouldGenerateKeyReplacingSingleWhitespaceWithDash() {
        assertThat(keyGenerator.generateKeyFromName("atletico mineiro"), equalTo("atletico-mineiro"));
        assertThat(keyGenerator.generateKeyFromName("atletico\tmineiro"), equalTo("atletico-mineiro"));
        assertThat(keyGenerator.generateKeyFromName("atletico\nmineiro"), equalTo("atletico-mineiro"));
        assertThat(keyGenerator.generateKeyFromName("atletico\rmineiro"), equalTo("atletico-mineiro"));
    }

    @Test
    public void shouldGenerateKeyReplacingMultipleSequentialWhitespacesWithOneDash() {
        assertThat(keyGenerator.generateKeyFromName("atletico  \t\n\r  mineiro"), equalTo("atletico-mineiro"));
    }

    @Test
    public void shouldGenerateKeyReplacingSingleUnderscoreWithDash() {
        assertThat(keyGenerator.generateKeyFromName("atletico_mineiro"), equalTo("atletico-mineiro"));
    }

    @Test
    public void shouldGenerateKeyReplacingMultipleSequentialUnderscoresWithSingleDash() {
        assertThat(keyGenerator.generateKeyFromName("atletico____mineiro"), equalTo("atletico-mineiro"));
    }

    @Test
    public void shouldGenerateKeyReplacingMultipleSequentialDashesWithSingleDash() {
        assertThat(keyGenerator.generateKeyFromName("atletico----mineiro"), equalTo("atletico-mineiro"));
    }

    @Test
    public void shouldGenerateKeyReplacingUpperCaseByLowerCase() {
        assertThat(keyGenerator.generateKeyFromName("Atletico"), is(equalTo("atletico")));
        assertThat(keyGenerator.generateKeyFromName("ATLETICO"), is(equalTo("atletico")));
    }

    @Test
    public void shouldGenerateKeyStrippingAccents() {
        assertThat(keyGenerator.generateKeyFromName("áéíóúý"), is(equalTo("aeiouy")));
        assertThat(keyGenerator.generateKeyFromName("ÁÉÍÓÚÝ"), is(equalTo("aeiouy")));
        assertThat(keyGenerator.generateKeyFromName("àèìòù"), is(equalTo("aeiou")));
        assertThat(keyGenerator.generateKeyFromName("ÀÈÌÒÙ"), is(equalTo("aeiou")));
        assertThat(keyGenerator.generateKeyFromName("âêîôû"), is(equalTo("aeiou")));
        assertThat(keyGenerator.generateKeyFromName("ÂÊÎÔÛ"), is(equalTo("aeiou")));
        assertThat(keyGenerator.generateKeyFromName("äëïöüÿ"), is(equalTo("aeiouy")));
        assertThat(keyGenerator.generateKeyFromName("ÄËÏÖÜŸ"), is(equalTo("aeiouy")));
        assertThat(keyGenerator.generateKeyFromName("ãõñ"), is(equalTo("aon")));
        assertThat(keyGenerator.generateKeyFromName("ÃÕÑ"), is(equalTo("aon")));
        assertThat(keyGenerator.generateKeyFromName("ç"), is(equalTo("c")));
        assertThat(keyGenerator.generateKeyFromName("Ç"), is(equalTo("c")));
        assertThat(keyGenerator.generateKeyFromName("Ç"), is(equalTo("c")));
    }

    @Test
    public void shouldGenerateKeyTrimmingWhitespace() {
        assertThat(keyGenerator.generateKeyFromName("  test   "), is(equalTo("test")));
    }

    @Test
    public void shouldGenerateKeyStrippingNonAlphanumericAsciiCharactersExceptDash() {
        assertThat(keyGenerator.generateKeyFromName("-!@#$%^&*+=[]{}()<>\\|/?'\"`~;:.,"), is(equalTo("-")));
    }
}
