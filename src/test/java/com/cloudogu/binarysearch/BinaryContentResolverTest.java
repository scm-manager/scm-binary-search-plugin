/*
 * Copyright (c) 2020 - present Cloudogu GmbH
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 */

package com.cloudogu.binarysearch;

import org.apache.commons.codec.Resources;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class BinaryContentResolverTest {

  private final BinaryContentResolver contentResolver = new BinaryContentResolver();

  @Test
  void shouldParsePdfContent() throws IOException {
    try (InputStream stream = Resources.getInputStream("com/cloudogu/binarysearch/sample.pdf")) {
      String content = contentResolver.resolveContent(stream);

      assertThat(content).contains("Lorem Ipsum");
    }
  }

  @Test
  void shouldParseDocxContent() throws IOException {
    try (InputStream stream = Resources.getInputStream("com/cloudogu/binarysearch/sample.docx")) {
      String content = contentResolver.resolveContent(stream);

      assertThat(content).contains("hitchhikers secret");
    }
  }

  @Test
  void shouldParseXlsxContent() throws IOException {
    try (InputStream stream = Resources.getInputStream("com/cloudogu/binarysearch/sample.xlsx")) {
      String content = contentResolver.resolveContent(stream);

      assertThat(content)
        .contains("many")
        .contains("columns")
        .contains("with")
        .contains("lots")
        .contains("of")
        .contains("content");
    }
  }

  @Test
  void shouldParsePptxContent() throws IOException {
    try (InputStream stream = Resources.getInputStream("com/cloudogu/binarysearch/sample.pptx")) {
      String content = contentResolver.resolveContent(stream);

      assertThat(content).contains("Nice presentation");
    }
  }

  @Test
  void shouldParseMp3Content() throws IOException {
    try (InputStream stream = Resources.getInputStream("com/cloudogu/binarysearch/sample.mp3")) {
      String content = contentResolver.resolveContent(stream);

      assertThat(content).contains("Kevin MacLeod");
    }
  }
}
