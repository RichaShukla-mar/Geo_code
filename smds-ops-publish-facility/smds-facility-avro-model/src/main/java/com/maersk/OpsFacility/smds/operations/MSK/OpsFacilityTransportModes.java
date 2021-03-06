/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.maersk.OpsFacility.smds.operations.MSK;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
/** Ops Facility TransportModes details */
@org.apache.avro.specific.AvroGenerated
public class OpsFacilityTransportModes extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 3364855024129467221L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"OpsFacilityTransportModes\",\"namespace\":\"com.maersk.OpsFacility.smds.operations.MSK\",\"doc\":\"Ops Facility TransportModes details\",\"fields\":[{\"name\":\"TransportMode\",\"type\":[\"null\",\"string\"]},{\"name\":\"TransportCode\",\"type\":[\"null\",\"string\"]},{\"name\":\"TransportDescription\",\"type\":[\"null\",\"string\"]},{\"name\":\"ValidThroughDate\",\"type\":[\"null\",\"string\"]}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<OpsFacilityTransportModes> ENCODER =
      new BinaryMessageEncoder<OpsFacilityTransportModes>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<OpsFacilityTransportModes> DECODER =
      new BinaryMessageDecoder<OpsFacilityTransportModes>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<OpsFacilityTransportModes> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<OpsFacilityTransportModes> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<OpsFacilityTransportModes>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this OpsFacilityTransportModes to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a OpsFacilityTransportModes from a ByteBuffer. */
  public static OpsFacilityTransportModes fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.CharSequence TransportMode;
  @Deprecated public java.lang.CharSequence TransportCode;
  @Deprecated public java.lang.CharSequence TransportDescription;
  @Deprecated public java.lang.CharSequence ValidThroughDate;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public OpsFacilityTransportModes() {}

  /**
   * All-args constructor.
   * @param TransportMode The new value for TransportMode
   * @param TransportCode The new value for TransportCode
   * @param TransportDescription The new value for TransportDescription
   * @param ValidThroughDate The new value for ValidThroughDate
   */
  public OpsFacilityTransportModes(java.lang.CharSequence TransportMode, java.lang.CharSequence TransportCode, java.lang.CharSequence TransportDescription, java.lang.CharSequence ValidThroughDate) {
    this.TransportMode = TransportMode;
    this.TransportCode = TransportCode;
    this.TransportDescription = TransportDescription;
    this.ValidThroughDate = ValidThroughDate;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return TransportMode;
    case 1: return TransportCode;
    case 2: return TransportDescription;
    case 3: return ValidThroughDate;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: TransportMode = (java.lang.CharSequence)value$; break;
    case 1: TransportCode = (java.lang.CharSequence)value$; break;
    case 2: TransportDescription = (java.lang.CharSequence)value$; break;
    case 3: ValidThroughDate = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'TransportMode' field.
   * @return The value of the 'TransportMode' field.
   */
  public java.lang.CharSequence getTransportMode() {
    return TransportMode;
  }

  /**
   * Sets the value of the 'TransportMode' field.
   * @param value the value to set.
   */
  public void setTransportMode(java.lang.CharSequence value) {
    this.TransportMode = value;
  }

  /**
   * Gets the value of the 'TransportCode' field.
   * @return The value of the 'TransportCode' field.
   */
  public java.lang.CharSequence getTransportCode() {
    return TransportCode;
  }

  /**
   * Sets the value of the 'TransportCode' field.
   * @param value the value to set.
   */
  public void setTransportCode(java.lang.CharSequence value) {
    this.TransportCode = value;
  }

  /**
   * Gets the value of the 'TransportDescription' field.
   * @return The value of the 'TransportDescription' field.
   */
  public java.lang.CharSequence getTransportDescription() {
    return TransportDescription;
  }

  /**
   * Sets the value of the 'TransportDescription' field.
   * @param value the value to set.
   */
  public void setTransportDescription(java.lang.CharSequence value) {
    this.TransportDescription = value;
  }

  /**
   * Gets the value of the 'ValidThroughDate' field.
   * @return The value of the 'ValidThroughDate' field.
   */
  public java.lang.CharSequence getValidThroughDate() {
    return ValidThroughDate;
  }

  /**
   * Sets the value of the 'ValidThroughDate' field.
   * @param value the value to set.
   */
  public void setValidThroughDate(java.lang.CharSequence value) {
    this.ValidThroughDate = value;
  }

  /**
   * Creates a new OpsFacilityTransportModes RecordBuilder.
   * @return A new OpsFacilityTransportModes RecordBuilder
   */
  public static com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder newBuilder() {
    return new com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder();
  }

  /**
   * Creates a new OpsFacilityTransportModes RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new OpsFacilityTransportModes RecordBuilder
   */
  public static com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder newBuilder(com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder other) {
    return new com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder(other);
  }

  /**
   * Creates a new OpsFacilityTransportModes RecordBuilder by copying an existing OpsFacilityTransportModes instance.
   * @param other The existing instance to copy.
   * @return A new OpsFacilityTransportModes RecordBuilder
   */
  public static com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder newBuilder(com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes other) {
    return new com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder(other);
  }

  /**
   * RecordBuilder for OpsFacilityTransportModes instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<OpsFacilityTransportModes>
    implements org.apache.avro.data.RecordBuilder<OpsFacilityTransportModes> {

    private java.lang.CharSequence TransportMode;
    private java.lang.CharSequence TransportCode;
    private java.lang.CharSequence TransportDescription;
    private java.lang.CharSequence ValidThroughDate;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.TransportMode)) {
        this.TransportMode = data().deepCopy(fields()[0].schema(), other.TransportMode);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.TransportCode)) {
        this.TransportCode = data().deepCopy(fields()[1].schema(), other.TransportCode);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.TransportDescription)) {
        this.TransportDescription = data().deepCopy(fields()[2].schema(), other.TransportDescription);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.ValidThroughDate)) {
        this.ValidThroughDate = data().deepCopy(fields()[3].schema(), other.ValidThroughDate);
        fieldSetFlags()[3] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing OpsFacilityTransportModes instance
     * @param other The existing instance to copy.
     */
    private Builder(com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.TransportMode)) {
        this.TransportMode = data().deepCopy(fields()[0].schema(), other.TransportMode);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.TransportCode)) {
        this.TransportCode = data().deepCopy(fields()[1].schema(), other.TransportCode);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.TransportDescription)) {
        this.TransportDescription = data().deepCopy(fields()[2].schema(), other.TransportDescription);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.ValidThroughDate)) {
        this.ValidThroughDate = data().deepCopy(fields()[3].schema(), other.ValidThroughDate);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'TransportMode' field.
      * @return The value.
      */
    public java.lang.CharSequence getTransportMode() {
      return TransportMode;
    }

    /**
      * Sets the value of the 'TransportMode' field.
      * @param value The value of 'TransportMode'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder setTransportMode(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.TransportMode = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'TransportMode' field has been set.
      * @return True if the 'TransportMode' field has been set, false otherwise.
      */
    public boolean hasTransportMode() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'TransportMode' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder clearTransportMode() {
      TransportMode = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'TransportCode' field.
      * @return The value.
      */
    public java.lang.CharSequence getTransportCode() {
      return TransportCode;
    }

    /**
      * Sets the value of the 'TransportCode' field.
      * @param value The value of 'TransportCode'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder setTransportCode(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.TransportCode = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'TransportCode' field has been set.
      * @return True if the 'TransportCode' field has been set, false otherwise.
      */
    public boolean hasTransportCode() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'TransportCode' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder clearTransportCode() {
      TransportCode = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'TransportDescription' field.
      * @return The value.
      */
    public java.lang.CharSequence getTransportDescription() {
      return TransportDescription;
    }

    /**
      * Sets the value of the 'TransportDescription' field.
      * @param value The value of 'TransportDescription'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder setTransportDescription(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.TransportDescription = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'TransportDescription' field has been set.
      * @return True if the 'TransportDescription' field has been set, false otherwise.
      */
    public boolean hasTransportDescription() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'TransportDescription' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder clearTransportDescription() {
      TransportDescription = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'ValidThroughDate' field.
      * @return The value.
      */
    public java.lang.CharSequence getValidThroughDate() {
      return ValidThroughDate;
    }

    /**
      * Sets the value of the 'ValidThroughDate' field.
      * @param value The value of 'ValidThroughDate'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder setValidThroughDate(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.ValidThroughDate = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'ValidThroughDate' field has been set.
      * @return True if the 'ValidThroughDate' field has been set, false otherwise.
      */
    public boolean hasValidThroughDate() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'ValidThroughDate' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityTransportModes.Builder clearValidThroughDate() {
      ValidThroughDate = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public OpsFacilityTransportModes build() {
      try {
        OpsFacilityTransportModes record = new OpsFacilityTransportModes();
        record.TransportMode = fieldSetFlags()[0] ? this.TransportMode : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.TransportCode = fieldSetFlags()[1] ? this.TransportCode : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.TransportDescription = fieldSetFlags()[2] ? this.TransportDescription : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.ValidThroughDate = fieldSetFlags()[3] ? this.ValidThroughDate : (java.lang.CharSequence) defaultValue(fields()[3]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<OpsFacilityTransportModes>
    WRITER$ = (org.apache.avro.io.DatumWriter<OpsFacilityTransportModes>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<OpsFacilityTransportModes>
    READER$ = (org.apache.avro.io.DatumReader<OpsFacilityTransportModes>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
