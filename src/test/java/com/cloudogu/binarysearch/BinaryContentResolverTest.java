/*
 * MIT License
 *
 * Copyright (c) 2020-present Cloudogu GmbH and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
