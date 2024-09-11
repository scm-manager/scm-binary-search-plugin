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
