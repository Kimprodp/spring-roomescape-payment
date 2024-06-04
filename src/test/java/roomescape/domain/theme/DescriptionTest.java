package roomescape.domain.theme;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import roomescape.BaseTest;

class DescriptionTest extends BaseTest {

    @Test
    void 설명이_10자_미만일_경우_예외_발생() {
        // given
        String name = "123456789";

        // when, then
        assertThatThrownBy(() -> new Description(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
