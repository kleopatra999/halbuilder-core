package com.theoryinpractise.halbuilder;

import com.theoryinpractise.halbuilder.impl.ContentType;
import org.testng.annotations.Test;

import static com.google.common.truth.Truth.assertThat;

public class ContentTypeTest {

  @Test
  public void testContentTypeEquality() {
    final ContentType contentType = new ContentType("application/xml");
    assertThat(contentType.equals(new ContentType("application/xml"))).isTrue();
    assertThat(contentType.equals(new ContentType("application/json"))).isFalse();
    assertThat(contentType.equals(contentType)).isTrue();
    assertThat(contentType.equals(null)).isFalse();
  }

  @Test
  public void testContentTypeCreation() {
    assertThat(new ContentType("application/xml").getType()).isEqualTo("application");
    assertThat(new ContentType("application/xml").getSubType()).isEqualTo("xml");
    assertThat(new ContentType("{application/hal+json, q=1000}").getType()).isEqualTo("application");
    assertThat(new ContentType("{application/hal+json, q=1000}").getSubType()).isEqualTo("hal+json");
    assertThat(new ContentType("*/json").getType()).isEqualTo("*");
    assertThat(new ContentType("*/json").getSubType()).isEqualTo("json");
  }

  @Test
  public void testContentTypeMatching() {
    assertThat(new ContentType("application/xml").matches("application/xml")).isTrue();
    assertThat(new ContentType("application/xml").matches(new ContentType("application/xml"))).isTrue();
    assertThat(new ContentType("application/xml").matches(new ContentType("application/*"))).isTrue();
    assertThat(new ContentType("application/xml").matches(new ContentType("*/*"))).isTrue();
    assertThat(new ContentType("application/xml").matches(new ContentType("*/xml"))).isTrue();
    assertThat(new ContentType("application/xml").matches(new ContentType("*/json"))).isFalse();
    assertThat(new ContentType("*/*").matches(new ContentType("application/xml"))).isFalse();
    assertThat(new ContentType("application/json").matches(new ContentType("{application/json; q=1000}"))).isTrue();
    assertThat(new ContentType("application/json").matches(new ContentType("{application/json;q=1000}"))).isTrue();
    assertThat(new ContentType("application/json").matches(new ContentType("{application/json, q=1000}"))).isTrue();
    assertThat(new ContentType("application/json").matches(new ContentType("{application/json,q=1000}"))).isTrue();
    assertThat(new ContentType("application/hal+json").matches(new ContentType("{application/hal+json; q=1000}"))).isTrue();
    assertThat(new ContentType("application/hal+json").matches(new ContentType("{application/hal+json;q=1000}"))).isTrue();
    assertThat(new ContentType("application/hal+json").matches(new ContentType("{application/hal+json, q=1000}"))).isTrue();
    assertThat(new ContentType("application/hal+json").matches(new ContentType("{application/hal+json,q=1000}"))).isTrue();
    assertThat(new ContentType("application/hal+json").matches(new ContentType("{*/hal+json ,q=1000}"))).isTrue();
    assertThat(new ContentType("*/*").matches(new ContentType("{application/hal+json ,q=1000}"))).isFalse();
  }

}
