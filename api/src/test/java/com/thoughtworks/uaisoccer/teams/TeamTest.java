package com.thoughtworks.uaisoccer.teams;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class TeamTest {

    Team team;
    Team slightlyDifferentTeam;
    Team veryDifferentTeam;
    Team identicalTeam;

    @Before
    public void setUp() {
        team = new Team("Cruzeiro Esporte Clube", "cruzeiro");
        team.setId(1l);
        identicalTeam = new Team("Cruzeiro Esporte Clube", "cruzeiro");
        identicalTeam.setId(1l);
        slightlyDifferentTeam = new Team("Cruzeiro Esporte Clube", "cruzeiro");
        slightlyDifferentTeam.setId(5l);
        veryDifferentTeam = new Team("Inter de Milão", "inter-de-milao");
        veryDifferentTeam.setId(9l);
    }

    @SuppressWarnings("ObjectEqualsNull")
    @Test
    public void shouldNotBeEqualsToNull() {
        assertThat(team.equals(null), is(false));
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void shouldNotBeEqualsToDifferentClassType() {
        String anotherType = "a string";
        assertThat(team.equals(anotherType), is(false));
    }

    @Test
    public void shouldNotBeEqualsToSlightlyDifferentTeam() {
        assertThat(team.equals(slightlyDifferentTeam), is(false));
    }

    @Test
    public void shouldNotHaveSameHashCodeThatSlightlyDifferentTeam() {
        assertThat(team.hashCode(), not(equalTo(slightlyDifferentTeam.hashCode())));
    }

    @Test
    public void shouldNotBeEqualsToVeryDifferentTeam() {
        assertThat(team.equals(veryDifferentTeam), is(false));
    }

    @Test
    public void shouldNotHaveSameHashCodeThatVeryDifferentTeam() {
        assertThat(team.hashCode(), not(equalTo(veryDifferentTeam.hashCode())));
    }

    @Test
    public void shouldBeEqualsToIdenticalTeam() {
        assertThat(team.equals(identicalTeam), is(true));
    }

    @Test
    public void shouldHaveSameHashCodeToIdenticalTeam() {
        assertThat(team.hashCode(), is(Matchers.equalTo(identicalTeam.hashCode())));
    }
}