package com.google.protobuf.utf8validation;

import static org.junit.Assert.assertThrows;

import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Utf8ValidationProto3GeneratedTest {
  // syntax: proto3
  // java_string_check_utf8: unset
  // enforce_utf8: unset
  // regular field
  // validates: yes
  @Test
  public void testUnsetUnset_validates() throws Exception {
    // Proto3 default: Validation enabled
    byte[] serialized =
        new byte[] {
          10, // tag for field 1
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () -> Utf8TestProto3.parseFrom(serialized, ExtensionRegistryLite.getEmptyRegistry()));
  }

  // syntax: proto3
  // java_string_check_utf8: true
  // enforce_utf8: unset
  // regular field
  // validates: yes
  @Test
  public void testCheckedUnset_validates() throws Exception {
    // Proto3 with java_string_check_utf8 = true: Validation enabled
    byte[] serialized =
        new byte[] {
          10, // tag for field 1
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () ->
            Utf8TestProto3Checked.parseFrom(serialized, ExtensionRegistryLite.getEmptyRegistry()));
  }

  // syntax: proto3
  // java_string_check_utf8: false
  // enforce_utf8: unset
  // regular field
  // validates: yes
  @Test
  public void testUncheckedUnset_validates() throws Exception {
    byte[] serialized =
        new byte[] {
          10, // tag for field 1
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () ->
            Utf8TestProto3Unchecked.parseFrom(
                serialized, ExtensionRegistryLite.getEmptyRegistry()));
  }
}
