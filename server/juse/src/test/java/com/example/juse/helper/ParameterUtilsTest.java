package com.example.juse.helper;

import com.example.juse.board.entity.Board;
import com.example.juse.helper.filterings.ParameterUtils;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

public class ParameterUtilsTest {


    @Test
    public void givenStringWithComma_whenToCollectionInvoked_thenCollectionReturn() {
        String test = "java, react,mysql ";

        Collection<String> result = ParameterUtils.toCollection(test);

        String expected1 = "java";
        String expected2 = "react";
        String expected3 = "mysql";

        assertThat(result.contains(expected1)).isTrue();
        assertThat(result.contains(expected2)).isTrue();
        assertThat(result.contains(expected3)).isTrue();
    }

    @Test
    public void enumTest() {
        String type = "OPENING";
        String enumValue = Board.Status.OPENING.toString();

        assertThat(type).isEqualTo(enumValue);
    }

    @Test
    public void numberTest() {
        String period = "1";

        System.out.println(period.toLowerCase());
    }
}
