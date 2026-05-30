package com.google.protobuf.utf8validation;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Utf8ValidationEdition2023DynamicMessageTest {
  // edition: 2023
  // regular field
  // validates: yes
  @Test
  public void testUnsetUnset_validates() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
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
            DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry()));
  }

  // edition: 2023
  // regular field
  // validates: yes
  @Test
  public void testUnsetDefault_validates() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    byte[] serialized =
        new byte[] {
          18, // tag for field 2
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () ->
            DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry()));
  }

  // edition: 2023
  // regular field
  // validates: yes
  @Test
  public void testUnsetVerify_validates() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
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
            DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry()));
  }

  // edition: 2023
  // regular field
  // validates: yes
  @Test
  public void testVerifyUnset_validates() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    byte[] serialized =
        new byte[] {
          34, // tag for field 4
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () ->
            DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry()));
  }

  // edition: 2023
  // regular field
  // validates: yes
  @Test
  public void testVerifyDefault_validates() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    byte[] serialized =
        new byte[] {
          42, // tag for field 5
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () ->
            DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry()));
  }

  // edition: 2023
  // regular field
  // validates: yes
  @Test
  public void testVerifyVerify_validates() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    byte[] serialized =
        new byte[] {
          50, // tag for field 6
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () ->
            DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry()));
  }

  // edition: 2023
  // regular field
  // validates: no
  @Test
  public void testNoneUnset_doesNotValidate() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    FieldDescriptor field = descriptor.findFieldByName("none_unset");
    byte[] serialized =
        new byte[] {
          58, // tag for field 7
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    DynamicMessage msg =
        DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry());
    assertThat(msg.hasField(field)).isTrue();
    assertThat(msg.getField(field)).isEqualTo("\uFFFD\uFFFD");
  }

  // edition: 2023
  // regular field
  // validates: no
  @Test
  public void testNoneDefault_doesNotValidate() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    FieldDescriptor field = descriptor.findFieldByName("none_default");
    byte[] serialized =
        new byte[] {
          66, // tag for field 8
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    DynamicMessage msg =
        DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry());
    assertThat(msg.hasField(field)).isTrue();
    assertThat(msg.getField(field)).isEqualTo("\uFFFD\uFFFD");
  }

  // edition: 2023
  // regular field
  // validates: yes
  @Test
  public void testNoneVerify_validates() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    byte[] serialized =
        new byte[] {
          74, // tag for field 9
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () ->
            DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry()));
  }

  // edition: 2023
  // extension
  // validates: yes
  @Test
  public void testUnsetUnsetExt_validates() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    FieldDescriptor extField = descriptor.getFile().findExtensionByName("ext_unset_unset");
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    registry.add(extField);

    byte[] serialized =
        new byte[] {
          (byte) 0xAA,
          (byte) 0x06, // tag for ext 101
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () -> DynamicMessage.parseFrom(descriptor, serialized, registry));
  }

  // edition: 2023
  // extension
  // validates: yes
  @Test
  public void testUnsetDefaultExt_validates() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    FieldDescriptor extField = descriptor.getFile().findExtensionByName("ext_unset_default");
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    registry.add(extField);

    byte[] serialized =
        new byte[] {
          (byte) 0xB2,
          (byte) 0x06, // tag for ext 102
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () -> DynamicMessage.parseFrom(descriptor, serialized, registry));
  }

  // edition: 2023
  // extension
  // validates: yes
  @Test
  public void testUnsetVerifyExt_validates() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    FieldDescriptor extField = descriptor.getFile().findExtensionByName("ext_unset_verify");
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    registry.add(extField);

    byte[] serialized =
        new byte[] {
          (byte) 0xBA,
          (byte) 0x06, // tag for ext 103
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () -> DynamicMessage.parseFrom(descriptor, serialized, registry));
  }

  // edition: 2023
  // extension
  // validates: yes
  @Test
  public void testVerifyUnsetExt_validates() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    FieldDescriptor extField = descriptor.getFile().findExtensionByName("ext_verify_unset");
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    registry.add(extField);

    byte[] serialized =
        new byte[] {
          (byte) 0xC2,
          (byte) 0x06, // tag for ext 104
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () -> DynamicMessage.parseFrom(descriptor, serialized, registry));
  }

  // edition: 2023
  // extension
  // validates: yes
  @Test
  public void testVerifyDefaultExt_validates() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    FieldDescriptor extField = descriptor.getFile().findExtensionByName("ext_verify_default");
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    registry.add(extField);

    byte[] serialized =
        new byte[] {
          (byte) 0xCA,
          (byte) 0x06, // tag for ext 105
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () -> DynamicMessage.parseFrom(descriptor, serialized, registry));
  }

  // edition: 2023
  // extension
  // validates: yes
  @Test
  public void testVerifyVerifyExt_validates() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    FieldDescriptor extField = descriptor.getFile().findExtensionByName("ext_verify_verify");
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    registry.add(extField);

    byte[] serialized =
        new byte[] {
          (byte) 0xD2,
          (byte) 0x06, // tag for ext 106
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () -> DynamicMessage.parseFrom(descriptor, serialized, registry));
  }

  // edition: 2023
  // extension
  // validates: no
  @Test
  public void testNoneUnsetExt_doesNotValidate() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    FieldDescriptor extField = descriptor.getFile().findExtensionByName("ext_none_unset");
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    registry.add(extField);

    byte[] serialized =
        new byte[] {
          (byte) 0xDA,
          (byte) 0x06, // tag for ext 107
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    DynamicMessage msg = DynamicMessage.parseFrom(descriptor, serialized, registry);
    assertThat(msg.hasField(extField)).isTrue();
    assertThat(msg.getField(extField)).isEqualTo("\uFFFD\uFFFD");
  }

  // edition: 2023
  // extension
  // validates: no
  @Test
  public void testNoneDefaultExt_doesNotValidate() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    FieldDescriptor extField = descriptor.getFile().findExtensionByName("ext_none_default");
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    registry.add(extField);

    byte[] serialized =
        new byte[] {
          (byte) 0xE2,
          (byte) 0x06, // tag for ext 108
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    DynamicMessage msg = DynamicMessage.parseFrom(descriptor, serialized, registry);
    assertThat(msg.hasField(extField)).isTrue();
    assertThat(msg.getField(extField)).isEqualTo("\uFFFD\uFFFD");
  }

  // edition: 2023
  // extension
  // validates: yes
  @Test
  public void testNoneVerifyExt_validates() throws Exception {
    Descriptor descriptor = Utf8TestEditions2023.getDescriptor();
    FieldDescriptor extField = descriptor.getFile().findExtensionByName("ext_none_verify");
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    registry.add(extField);

    byte[] serialized =
        new byte[] {
          (byte) 0xEA,
          (byte) 0x06, // tag for ext 109
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () -> DynamicMessage.parseFrom(descriptor, serialized, registry));
  }
}
