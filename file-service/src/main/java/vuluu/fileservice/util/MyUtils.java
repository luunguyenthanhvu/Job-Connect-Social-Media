package vuluu.fileservice.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MyUtils {

  /**
   * This method return a userID from token get from filter
   *
   * @return userID
   */
  public String getUserId() {
    return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public byte[] decompress(byte[] compressedData) throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedData);
    try (GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
      return gzipInputStream.readAllBytes();
    }
  }
}
