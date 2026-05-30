package com.google.protobuf.utf8validation;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import com.google.protobuf.DescriptorProtos.DescriptorProto;
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
public class Utf8ValidationProto3DynamicMessageTest {
  // syntax: proto3
  // java_string_check_utf8: unset
  // enforce_utf8: unset
  // regular field
  // validates: yes
  @Test
  public void testUnsetUnset_validates() throws Exception {
    // Proto3 default: Validation enabled
    Descriptor descriptor = buildDescriptor(null, null);
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
            DynamicMessage.parseFrom(
                descriptor, serialized, ExtensionRegistry.getGeneratedRegistry()));
  }

  // syntax: proto3
  // java_string_check_utf8: true
  // enforce_utf8: unset
  // regular field
  // validates: yes
  @Test
  public void testCheckedUnset_validates() throws Exception {
    Descriptor descriptor = buildDescriptor(null, true);
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
            DynamicMessage.parseFrom(
                descriptor, serialized, ExtensionRegistry.getGeneratedRegistry()));
  }

  // syntax: proto3
  // java_string_check_utf8: false
  // enforce_utf8: unset
  // regular field
  // validates: yes
  @Test
  public void testUncheckedUnset_validates() throws Exception {
    Descriptor descriptor = buildDescriptor(null, false);
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
            DynamicMessage.parseFrom(
                descriptor, serialized, ExtensionRegistry.getGeneratedRegistry()));
  }

  // syntax: proto3
  // java_string_check_utf8: true
  // enforce_utf8: true
  // regular field
  // validates: yes
  @Test
  public void testCheckedEnforced_validates() throws Exception {
    Descriptor descriptor = buildDescriptor(true, true);
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
            DynamicMessage.parseFrom(
                descriptor, serialized, ExtensionRegistry.getGeneratedRegistry()));
  }

  // syntax: proto3
  // java_string_check_utf8: false
  // enforce_utf8: true
  // regular field
  // validates: yes
  @Test
  public void testUncheckedEnforced_validates() throws Exception {
    Descriptor descriptor = buildDescriptor(true, false);
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
            DynamicMessage.parseFrom(
                descriptor, serialized, ExtensionRegistry.getGeneratedRegistry()));
  }

  // syntax: proto3
  // java_string_check_utf8: unset
  // enforce_utf8: false
  // regular field
  // validates: no
  @Test
  public void testUnsetUnenforced_doesNotValidate() throws Exception {
    // Proto3 with [enforce_utf8 = false]: No validation
    Descriptor descriptor = buildDescriptor(false, null);
    FieldDescriptor field = descriptor.findFieldByName("value");
    byte[] serialized =
        new byte[] {
          (byte) ((field.getNumber() << 3) | 2), // tag
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    DynamicMessage msg =
        DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getGeneratedRegistry());
    // Parsing succeeded without exception
    assertThat(msg.hasField(field)).isTrue();
    assertThat(msg.getField(field)).isEqualTo("\uFFFD\uFFFD");
  }

  // syntax: proto3
  // java_string_check_utf8: true
  // enforce_utf8: false
  // regular field
  // validates: no
  @Test
  public void testCheckedUnenforced_doesNotValidate() throws Exception {
    Descriptor descriptor = buildDescriptor(false, true);
    FieldDescriptor field = descriptor.findFieldByName("value");
    byte[] serialized =
        new byte[] {
          (byte) ((field.getNumber() << 3) | 2), // tag
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    DynamicMessage msg =
        DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getGeneratedRegistry());
    // Parsing succeeded without exception
    assertThat(msg.hasField(field)).isTrue();
    assertThat(msg.getField(field)).isEqualTo("\uFFFD\uFFFD");
  }

  // syntax: proto3
  // java_string_check_utf8: false
  // enforce_utf8: false
  // regular field
  // validates: no
  @Test
  public void testUncheckedUnenforced_doesNotValidate() throws Exception {
    Descriptor descriptor = buildDescriptor(false, false);
    FieldDescriptor field = descriptor.findFieldByName("value");
    byte[] serialized =
        new byte[] {
          (byte) ((field.getNumber() << 3) | 2), // tag
          2, // length
          (byte) 0xC0,
          (byte) 0x80
        };

    DynamicMessage msg =
        DynamicMessage.parseFrom(descriptor, serialized, ExtensionRegistry.getGeneratedRegistry());
    // Parsing succeeded without exception
    assertThat(msg.hasField(field)).isTrue();
    assertThat(msg.getField(field)).isEqualTo("\uFFFD\uFFFD");
  }

  private static Descriptor buildDescriptor(Boolean enforceUtf8, Boolean javaStringCheckUtf8)
      throws Exception {
    FileDescriptorProto.Builder fileBuilder =
        FileDescriptorProto.newBuilder().setName("test_proto3.proto").setSyntax("proto3");

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
      fieldBuilder.setOptions(FieldOptions.newBuilder().setEnforceUtf8(enforceUtf8));
    }

    msgBuilder.addField(fieldBuilder);
    fileBuilder.addMessageType(msgBuilder);

    FileDescriptor fileDescriptor =
        FileDescriptor.buildFrom(fileBuilder.build(), new FileDescriptor[0]);
    return fileDescriptor.findMessageTypeByName("Utf8TestMessage");
  }
}
