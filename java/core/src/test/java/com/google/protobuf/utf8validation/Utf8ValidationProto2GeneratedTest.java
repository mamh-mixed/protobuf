package com.google.protobuf.utf8validation;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Utf8ValidationProto2GeneratedTest {
  private boolean isLite = true;

  // BEGIN FULL-RUNTIME
  @Before
  public void setUp() {
    isLite = false;
  }

  // END FULL-RUNTIME

  // syntax: proto2
  // java_string_check_utf8: unset
  // enforce_utf8: unset
  // regular field
  // validates: no
  @Test
  public void testUnsetUnset_doesNotValidate() throws Exception {
    byte[] serialized =
        new byte[] {
          10, // tag for field 1 (1 << 3) | 2 = 10
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    Utf8TestProto2 msg =
        Utf8TestProto2.parseFrom(serialized, ExtensionRegistryLite.getEmptyRegistry());
    assertThat(msg.hasUnsetUnset()).isTrue();
    assertThat(msg.getUnsetUnset()).isEqualTo("\uFFFD\uFFFD");
  }

  // syntax: proto2
  // java_string_check_utf8: false
  // enforce_utf8: unset
  // regular field
  // validates: no
  @Test
  public void testUncheckedUnset_doesNotValidate() throws Exception {
    byte[] serialized =
        new byte[] {
          10, // tag for field 1 (1 << 3) | 2 = 10
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    Utf8TestProto2Unchecked msg =
        Utf8TestProto2Unchecked.parseFrom(serialized, ExtensionRegistryLite.getEmptyRegistry());
    assertThat(msg.hasUncheckedUnset()).isTrue();
    assertThat(msg.getUncheckedUnset()).isEqualTo("\uFFFD\uFFFD");
  }

  // syntax: proto2
  // java_string_check_utf8: false
  // enforce_utf8: true
  // regular field
  // validates: no
  @Test
  public void testUncheckedEnforced_doesNotValidate() throws Exception {
    byte[] serialized =
        new byte[] {
          26, // tag for field 3 (3 << 3) | 2 = 26
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    Utf8TestProto2Unchecked msg =
        Utf8TestProto2Unchecked.parseFrom(serialized, ExtensionRegistryLite.getEmptyRegistry());
    assertThat(msg.hasUncheckedEnforced()).isTrue();
    assertThat(msg.getUncheckedEnforced()).isEqualTo("\uFFFD\uFFFD");
  }

  // syntax: proto2
  // java_string_check_utf8: true
  // enforce_utf8: unset
  // regular field
  // validates: yes
  @Test
  public void testCheckedUnset_validates() throws Exception {
    byte[] serialized =
        new byte[] {
          10, // tag for field 1
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    // Full-runtime validates string fields.
    assertThrows(
        InvalidProtocolBufferException.class,
        () ->
            Utf8TestProto2Checked.parseFrom(serialized, ExtensionRegistryLite.getEmptyRegistry()));
  }

  // syntax: proto2
  // java_string_check_utf8: true
  // enforce_utf8: true
  // regular field
  // validates: yes
  @Test
  public void testCheckedEnforced_validates() throws Exception {
    byte[] serialized =
        new byte[] {
          26, // tag for field 3
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () ->
            Utf8TestProto2Checked.parseFrom(serialized, ExtensionRegistryLite.getEmptyRegistry()));
  }

  // syntax: proto2
  // java_string_check_utf8: unset
  // enforce_utf8: true
  // extension
  // validates: no
  @Test
  public void testUnsetEnforcedExt_doesNotValidate() throws Exception {
    // [enforce_utf8 = true] has no effect in proto2
    ExtensionRegistryLite registry = ExtensionRegistryLite.newInstance();
    registry.add(Utf8TestProto2Proto.extUnsetEnforced);

    byte[] serialized =
        new byte[] {
          (byte) 0xB2,
          (byte) 0x06, // tag for field 102 (102 << 3 | 2 = 818 = 0xB2 0x06 in varint)
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    Utf8TestProto2 msg = Utf8TestProto2.parseFrom(serialized, registry);
    assertThat(msg.hasExtension(Utf8TestProto2Proto.extUnsetEnforced)).isTrue();
    assertThat(msg.getExtension(Utf8TestProto2Proto.extUnsetEnforced)).isEqualTo("\uFFFD\uFFFD");
  }

  // syntax: proto2
  // java_string_check_utf8: true
  // extension
  // validates: full-runtime only
  @Test
  public void testCheckedUnsetExt() throws Exception {
    ExtensionRegistryLite registry = ExtensionRegistryLite.newInstance();
    registry.add(Utf8TestProto2CheckedProto.extCheckedUnset);

    byte[] serialized =
        new byte[] {
          (byte) 0xA2,
          (byte) 0x06, // tag for field 100
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    if (isLite) {
      // Lite does not validate string extensions.
      Utf8TestProto2Checked msg = Utf8TestProto2Checked.parseFrom(serialized, registry);
      assertThat(msg.hasExtension(Utf8TestProto2CheckedProto.extCheckedUnset)).isTrue();
      assertThat(msg.getExtension(Utf8TestProto2CheckedProto.extCheckedUnset))
          .isEqualTo("\uFFFD\uFFFD");
    } else {
      // Full-runtime validates string extensions.
      assertThrows(
          InvalidProtocolBufferException.class,
          () -> Utf8TestProto2Checked.parseFrom(serialized, registry));
    }
  }
}
