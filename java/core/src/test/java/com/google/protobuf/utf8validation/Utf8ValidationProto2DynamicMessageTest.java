package com.google.protobuf.utf8validation;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.FeatureSet.Utf8Validation;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto;
import com.google.protobuf.DescriptorProtos.FieldOptions;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.DescriptorProtos.FileOptions;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Utf8ValidationProto2DynamicMessageTest {
  // syntax: proto2
  // java_string_check_utf8: unset
  // enforce_utf8: unset
  // regular field
  // validates: no
  @Test
  public void testUnsetUnset_doesNotValidate() throws Exception {
    // Proto2 default: No validation
    Descriptor descriptor = Utf8TestProto2.getDescriptor();
    FieldDescriptor field = descriptor.findFieldByName("unset_unset");
    byte[] serialized =
        new byte[] {
          (byte) ((field.getNumber() << 3) | 2), // tag
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    DynamicMessage msg =
        DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry());
    // Parsing succeeded without exception
    assertThat(msg.hasField(field)).isTrue();
    assertThat(msg.getField(field)).isEqualTo("\uFFFD\uFFFD");
  }

  // syntax: proto2
  // java_string_check_utf8: unset
  // enforce_utf8: true
  // regular field
  // validates: no
  @Test
  public void testUnsetEnforced_doesNotValidate() throws Exception {
    // Proto2 with [enforce_utf8 = true] on normal field: No validation in DynamicMessage
    Descriptor descriptor = Utf8TestProto2.getDescriptor();
    FieldDescriptor field = descriptor.findFieldByName("unset_enforced");
    byte[] serialized =
        new byte[] {
          (byte) ((field.getNumber() << 3) | 2), // tag
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    DynamicMessage msg =
        DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry());
    // Parsing succeeded without exception
    assertThat(msg.hasField(field)).isTrue();
    assertThat(msg.getField(field)).isEqualTo("\uFFFD\uFFFD");
  }

  // syntax: proto2
  // java_string_check_utf8: false
  // enforce_utf8: unset
  // regular field
  // validates: no
  @Test
  public void testUncheckedUnset_doesNotValidate() throws Exception {
    Descriptor descriptor = buildDescriptor(null, false);
    FieldDescriptor field = descriptor.findFieldByName("value");
    byte[] serialized =
        new byte[] {
          (byte) ((field.getNumber() << 3) | 2), // tag
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    DynamicMessage msg =
        DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry());
    // Parsing succeeded without exception
    assertThat(msg.hasField(field)).isTrue();
    assertThat(msg.getField(field)).isEqualTo("\uFFFD\uFFFD");
  }

  // syntax: proto2
  // java_string_check_utf8: false
  // enforce_utf8: true
  // regular field
  // validates: no
  @Test
  public void testUncheckedEnforced_doesNotValidate() throws Exception {
    Descriptor descriptor = buildDescriptor(true, false);
    FieldDescriptor field = descriptor.findFieldByName("value");
    byte[] serialized =
        new byte[] {
          (byte) ((field.getNumber() << 3) | 2), // tag
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    DynamicMessage msg =
        DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry());
    // Parsing succeeded without exception
    assertThat(msg.hasField(field)).isTrue();
    assertThat(msg.getField(field)).isEqualTo("\uFFFD\uFFFD");
  }

  // syntax: proto2
  // java_string_check_utf8: true
  // enforce_utf8: unset
  // regular field
  // validates: yes
  @Test
  public void testCheckedUnset_validates() throws Exception {
    // Proto2 with java_string_check_utf8 = true: Validation enabled
    Descriptor descriptor = Utf8TestProto2Checked.getDescriptor();
    FieldDescriptor field = descriptor.findFieldByName("checked_unset");
    byte[] serialized =
        new byte[] {
          (byte) ((field.getNumber() << 3) | 2), // tag
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () ->
            DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry()));
  }

  // syntax: proto2
  // java_string_check_utf8: true
  // enforce_utf8: true
  // regular field
  // validates: yes
  @Test
  public void testCheckedEnforced_validates() throws Exception {
    Descriptor descriptor = Utf8TestProto2Checked.getDescriptor();
    FieldDescriptor field = descriptor.findFieldByName("checked_enforced");
    byte[] serialized =
        new byte[] {
          (byte) ((field.getNumber() << 3) | 2), // tag
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () ->
            DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry()));
  }

  // syntax: proto2
  // java_string_check_utf8: true
  // enforce_utf8: false
  // regular field
  // validates: yes
  @Test
  public void testCheckedUnenforced_validates() throws Exception {
    Descriptor descriptor = buildDescriptor(false, true);
    FieldDescriptor field = descriptor.findFieldByName("value");
    byte[] serialized =
        new byte[] {
          (byte) ((field.getNumber() << 3) | 2), // tag
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () ->
            DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getEmptyRegistry()));
  }

  // syntax: proto2
  // java_string_check_utf8: unset
  // enforce_utf8: true
  // extension
  // validates: no
  @Test
  public void testUnsetEnforcedExt_doesNotValidate() throws Exception {
    // Proto2 with [enforce_utf8 = true] on extension: No validation in DynamicMessage
    Descriptor descriptor = Utf8TestProto2.getDescriptor();
    FieldDescriptor extField = descriptor.getFile().findExtensionByName("ext_unset_enforced");
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    registry.add(extField);

    byte[] serialized =
        new byte[] {
          (byte) 0xB2,
          (byte) 0x06, // tag for field 102 (102 << 3 | 2 = 818 = 0xB2 0x06 in varint)
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    DynamicMessage msg = DynamicMessage.parseFrom(descriptor, serialized, registry);
    // Parsing succeeded without exception
    assertThat(msg.hasField(extField)).isTrue();
    assertThat(msg.getField(extField)).isEqualTo("\uFFFD\uFFFD");
  }

  // syntax: proto2
  // java_string_check_utf8: unset
  // enforce_utf8: unset
  // extension
  // validates: no
  @Test
  public void testUnsetUnsetExt_doesNotValidate() throws Exception {
    // Proto2 with [enforce_utf8 = false] on extension: No validation
    Descriptor descriptor = Utf8TestProto2.getDescriptor();
    FieldDescriptor extField = descriptor.getFile().findExtensionByName("ext_unset_unset");
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    registry.add(extField);

    byte[] serialized =
        new byte[] {
          (byte) 0xA2,
          (byte) 0x06, // tag for field 100
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    DynamicMessage msg = DynamicMessage.parseFrom(descriptor, serialized, registry);
    // Parsing succeeded without exception
    assertThat(msg.hasField(extField)).isTrue();
    assertThat(msg.getField(extField)).isEqualTo("\uFFFD\uFFFD");
  }

  // syntax: proto2
  // java_string_check_utf8: true
  // extension
  // validates: yes
  @Test
  public void testCheckedUnsetExt_validates() throws Exception {
    Descriptor descriptor = Utf8TestProto2Checked.getDescriptor();
    FieldDescriptor extField = descriptor.getFile().findExtensionByName("ext_checked_unset");
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    registry.add(extField);

    byte[] serialized =
        new byte[] {
          (byte) 0xA2,
          (byte) 0x06, // tag for field 100
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    assertThrows(
        InvalidProtocolBufferException.class,
        () -> DynamicMessage.parseFrom(descriptor, serialized, registry));
  }

  private static Descriptor buildDescriptor(Boolean enforceUtf8, Boolean javaStringCheckUtf8)
      throws Exception {
    FileDescriptorProto.Builder fileBuilder =
        FileDescriptorProto.newBuilder().setName("test_proto2_dynamic.proto").setSyntax("proto2");

    if (javaStringCheckUtf8 != null) {
      fileBuilder.setOptions(FileOptions.newBuilder().setJavaStringCheckUtf8(javaStringCheckUtf8));
    }

    DescriptorProto.Builder msgBuilder = DescriptorProto.newBuilder().setName("Utf8TestMessage");

    FieldDescriptorProto.Builder fieldBuilder =
        FieldDescriptorProto.newBuilder()
            .setName("value")
            .setNumber(1)
            .setType(FieldDescriptorProto.Type.TYPE_STRING)
            .setLabel(FieldDescriptorProto.Label.LABEL_OPTIONAL);

    if (enforceUtf8 != null) {
      FieldOptions.Builder optionsBuilder = FieldOptions.newBuilder().setEnforceUtf8(enforceUtf8);
      if (!enforceUtf8) {
        optionsBuilder.getFeaturesBuilder().setUtf8Validation(Utf8Validation.NONE);
      }
      fieldBuilder.setOptions(optionsBuilder);
    }

    msgBuilder.addField(fieldBuilder);
    fileBuilder.addMessageType(msgBuilder);

    FileDescriptor fileDescriptor =
        FileDescriptor.buildFrom(fileBuilder.build(), new FileDescriptor[0]);
    return fileDescriptor.findMessageTypeByName("Utf8TestMessage");
  }
}
