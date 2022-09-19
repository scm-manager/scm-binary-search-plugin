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

import com.cloudogu.scm.search.BinaryFileContentResolver;
import org.apache.commons.codec.Resources;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import sonia.scm.plugin.Extension;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@Extension
public class BinaryContentResolver implements BinaryFileContentResolver {

  private static final Logger LOG = LoggerFactory.getLogger(BinaryContentResolver.class);

  @Override
  public String resolveContent(InputStream inputStream) {
    BodyContentHandler handler = new BodyContentHandler(-1);
    Thread.currentThread().setContextClassLoader(handler.getClass().getClassLoader());
    try {
      TikaConfig tikaConfig = createTikaConfig();
      new AutoDetectParser(tikaConfig).parse(inputStream, handler, new Metadata());
      return handler.toString();

    } catch (Exception e) {
      LOG.warn("Failed to parse binary file", e);
      return "";
    }
  }

  @Override
  public boolean isSupported(String contentType) {
    try {
      Set<MediaType> supportedTypes = new AutoDetectParser(createTikaConfig()).getSupportedTypes(new ParseContext());
      return supportedTypes.stream().anyMatch(mt -> mt.toString().equals(contentType));
    } catch (Exception e) {
      return false;
    }
  }

  private static TikaConfig createTikaConfig() throws TikaException, IOException, SAXException {
    return new TikaConfig(Resources.getInputStream("com/cloudogu/binarysearch/tika-config.xml"));
  }
}
