/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.maersk.facility.smds.operations.MSK;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
/** The generic avro schema used by publishers to publish events to the enterprise streaming service for Facility interface */
@org.apache.avro.specific.AvroGenerated
public class facilityMessage extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -7636768136327754103L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"facilityMessage\",\"namespace\":\"com.maersk.facility.smds.operations.MSK\",\"doc\":\"The generic avro schema used by publishers to publish events to the enterprise streaming service for Facility interface\",\"fields\":[{\"name\":\"facility\",\"type\":{\"type\":\"record\",\"name\":\"facility\",\"doc\":\"Facility core Information\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"type\",\"type\":\"string\"},{\"name\":\"extOwned\",\"type\":[\"null\",\"string\"]},{\"name\":\"status\",\"type\":\"string\"},{\"name\":\"extExposed\",\"type\":[\"null\",\"string\"]},{\"name\":\"url\",\"type\":[\"null\",\"string\"]},{\"name\":\"doDAAC\",\"type\":[\"null\",\"string\"]},{\"name\":\"address\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"address\",\"doc\":\"Facility Address\",\"fields\":[{\"name\":\"houseNumber\",\"type\":[\"null\",\"string\"]},{\"name\":\"street\",\"type\":[\"null\",\"string\"]},{\"name\":\"city\",\"type\":[\"null\",\"string\"]},{\"name\":\"postalCode\",\"type\":[\"null\",\"string\"]},{\"name\":\"poBox\",\"type\":[\"null\",\"string\"]},{\"name\":\"district\",\"type\":[\"null\",\"string\"]},{\"name\":\"territory\",\"type\":[\"null\",\"string\"]},{\"name\":\"countryName\",\"type\":[\"null\",\"string\"]},{\"name\":\"countryCode\",\"type\":[\"null\",\"string\"]},{\"name\":\"addressLine2\",\"type\":[\"null\",\"string\"]},{\"name\":\"addressLine3\",\"type\":[\"null\",\"string\"]},{\"name\":\"latitude\",\"type\":[\"null\",\"string\"]},{\"name\":\"longitude\",\"type\":[\"null\",\"string\"]},{\"name\":\"addressQualityCheckIndicator\",\"type\":[\"null\",\"string\"]}]}}},{\"name\":\"parent\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"parent\",\"doc\":\"Facility Parent\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"type\",\"type\":\"string\"},{\"name\":\"alternateCodes\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"parentAlternateCodes\",\"doc\":\"Parent AlternateCode details\",\"fields\":[{\"name\":\"codeType\",\"type\":[\"null\",\"string\"]},{\"name\":\"code\",\"type\":[\"null\",\"string\"]}]}}]}]}}]},{\"name\":\"facilityDetails\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"facilityDetail\",\"doc\":\"Facility Details\",\"fields\":[{\"name\":\"weightLimitCraneKg\",\"type\":[\"null\",\"string\"]},{\"name\":\"weightLimitYardKg\",\"type\":[\"null\",\"string\"]},{\"name\":\"vesselAgent\",\"type\":[\"null\",\"string\"]},{\"name\":\"gpsFlag\",\"type\":[\"null\",\"string\"]},{\"name\":\"gsmFlag\",\"type\":[\"null\",\"string\"]},{\"name\":\"oceanFreightPricing\",\"type\":[\"null\",\"string\"]},{\"name\":\"brand\",\"type\":[\"null\",\"string\"]},{\"name\":\"commFacilityType\",\"type\":[\"null\",\"string\"]},{\"name\":\"exportEnquiriesEmail\",\"type\":[\"null\",\"string\"]},{\"name\":\"importEnquiriesEmail\",\"type\":[\"null\",\"string\"]},{\"name\":\"facilityFunction\",\"type\":[\"null\",\"string\"]},{\"name\":\"facilityFunctionDesc\",\"type\":[\"null\",\"string\"]},{\"name\":\"internationalDialCode\",\"type\":[\"null\",\"string\"]},{\"name\":\"telephoneNumber\",\"type\":[\"null\",\"string\"]},{\"name\":\"facilityType\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"OpsFacilityType\",\"doc\":\"Ops Facility Type\",\"fields\":[{\"name\":\"code\",\"type\":[\"null\",\"string\"]},{\"name\":\"name\",\"type\":[\"null\",\"string\"]},{\"name\":\"masterType\",\"type\":[\"null\",\"string\"]},{\"name\":\"validThroughDate\",\"type\":[\"null\",\"long\"],\"logicalType\":\"timestamp-millis\"}]}}]}]}}]},{\"name\":\"alternateCodes\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"alternateCodes\",\"doc\":\"AlternateCodes Details\",\"fields\":[{\"name\":\"codeType\",\"type\":[\"null\",\"string\"]},{\"name\":\"code\",\"type\":[\"null\",\"string\"]}]}}},{\"name\":\"openingHours\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"openingHours\",\"doc\":\"Facility OpeningHours\",\"fields\":[{\"name\":\"day\",\"type\":[\"null\",\"string\"]},{\"name\":\"openTimeHours\",\"type\":[\"null\",\"string\"]},{\"name\":\"openTimeMinutes\",\"type\":[\"null\",\"string\"]},{\"name\":\"closeTimeHours\",\"type\":[\"null\",\"string\"]},{\"name\":\"closeTimeMinutes\",\"type\":[\"null\",\"string\"]}]}}]},{\"name\":\"transportModes\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"transportModes\",\"doc\":\"Facility TransportModes\",\"fields\":[{\"name\":\"transportMode\",\"type\":[\"null\",\"string\"]},{\"name\":\"transportCode\",\"type\":[\"null\",\"string\"]},{\"name\":\"transportDescription\",\"type\":[\"null\",\"string\"]},{\"name\":\"validThroughDate\",\"type\":[\"null\",\"long\"],\"logicalType\":\"timestamp-millis\"}]}}]},{\"name\":\"facilityServices\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"facilityServices\",\"doc\":\"FacilityServices Details\",\"fields\":[{\"name\":\"serviceName\",\"type\":[\"null\",\"string\"]},{\"name\":\"serviceCode\",\"type\":[\"null\",\"string\"]},{\"name\":\"serviceDescription\",\"type\":[\"null\",\"string\"]},{\"name\":\"validThroughDate\",\"type\":[\"null\",\"long\"],\"logicalType\":\"timestamp-millis\"}]}}]},{\"name\":\"fence\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"fence\",\"doc\":\"Facility Fence Details\",\"fields\":[{\"name\":\"name\",\"type\":[\"null\",\"string\"]},{\"name\":\"fenceType\",\"type\":[\"null\",\"string\"]}]}}]},{\"name\":\"bda\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"bda\",\"doc\":\"BDA details\",\"fields\":[{\"name\":\"name\",\"type\":[\"null\",\"string\"]},{\"name\":\"type\",\"type\":[\"null\",\"string\"]},{\"name\":\"bdaType\",\"type\":[\"null\",\"string\"]},{\"name\":\"alternateCodes\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"bdaAlternateCode\",\"doc\":\"BDA AlternateCode details\",\"fields\":[{\"name\":\"codeType\",\"type\":[\"null\",\"string\"]},{\"name\":\"code\",\"type\":[\"null\",\"string\"]}]}}]}]}}]},{\"name\":\"contactDetails\",\"type\":[\"null\",{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"contactDetails\",\"doc\":\"Facility Contact Details\",\"fields\":[{\"name\":\"firstName\",\"type\":[\"null\",\"string\"]},{\"name\":\"lastName\",\"type\":[\"null\",\"string\"]},{\"name\":\"jobTitle\",\"type\":[\"null\",\"string\"]},{\"name\":\"department\",\"type\":[\"null\",\"string\"]},{\"name\":\"internationalDialingCdPhone\",\"type\":[\"null\",\"string\"]},{\"name\":\"extension\",\"type\":[\"null\",\"string\"]},{\"name\":\"phoneNumber\",\"type\":[\"null\",\"string\"]},{\"name\":\"internationalDialingCdMobile\",\"type\":[\"null\",\"string\"]},{\"name\":\"mobileNumber\",\"type\":[\"null\",\"string\"]},{\"name\":\"internaltionalDialingCodeFax\",\"type\":[\"null\",\"string\"]},{\"name\":\"faxNumber\",\"type\":[\"null\",\"string\"]},{\"name\":\"emailAddress\",\"type\":[\"null\",\"string\"]},{\"name\":\"validThroughDate\",\"type\":[\"null\",\"long\"],\"logicalType\":\"timestamp-millis\"}]}}]}]}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<facilityMessage> ENCODER =
      new BinaryMessageEncoder<facilityMessage>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<facilityMessage> DECODER =
      new BinaryMessageDecoder<facilityMessage>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<facilityMessage> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<facilityMessage> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<facilityMessage>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this facilityMessage to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a facilityMessage from a ByteBuffer. */
  public static facilityMessage fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public com.maersk.facility.smds.operations.MSK.facility facility;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public facilityMessage() {}

  /**
   * All-args constructor.
   * @param facility The new value for facility
   */
  public facilityMessage(com.maersk.facility.smds.operations.MSK.facility facility) {
    this.facility = facility;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return facility;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: facility = (com.maersk.facility.smds.operations.MSK.facility)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'facility' field.
   * @return The value of the 'facility' field.
   */
  public com.maersk.facility.smds.operations.MSK.facility getFacility() {
    return facility;
  }

  /**
   * Sets the value of the 'facility' field.
   * @param value the value to set.
   */
  public void setFacility(com.maersk.facility.smds.operations.MSK.facility value) {
    this.facility = value;
  }

  /**
   * Creates a new facilityMessage RecordBuilder.
   * @return A new facilityMessage RecordBuilder
   */
  public static com.maersk.facility.smds.operations.MSK.facilityMessage.Builder newBuilder() {
    return new com.maersk.facility.smds.operations.MSK.facilityMessage.Builder();
  }

  /**
   * Creates a new facilityMessage RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new facilityMessage RecordBuilder
   */
  public static com.maersk.facility.smds.operations.MSK.facilityMessage.Builder newBuilder(com.maersk.facility.smds.operations.MSK.facilityMessage.Builder other) {
    return new com.maersk.facility.smds.operations.MSK.facilityMessage.Builder(other);
  }

  /**
   * Creates a new facilityMessage RecordBuilder by copying an existing facilityMessage instance.
   * @param other The existing instance to copy.
   * @return A new facilityMessage RecordBuilder
   */
  public static com.maersk.facility.smds.operations.MSK.facilityMessage.Builder newBuilder(com.maersk.facility.smds.operations.MSK.facilityMessage other) {
    return new com.maersk.facility.smds.operations.MSK.facilityMessage.Builder(other);
  }

  /**
   * RecordBuilder for facilityMessage instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<facilityMessage>
    implements org.apache.avro.data.RecordBuilder<facilityMessage> {

    private com.maersk.facility.smds.operations.MSK.facility facility;
    private com.maersk.facility.smds.operations.MSK.facility.Builder facilityBuilder;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.maersk.facility.smds.operations.MSK.facilityMessage.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.facility)) {
        this.facility = data().deepCopy(fields()[0].schema(), other.facility);
        fieldSetFlags()[0] = true;
      }
      if (other.hasFacilityBuilder()) {
        this.facilityBuilder = com.maersk.facility.smds.operations.MSK.facility.newBuilder(other.getFacilityBuilder());
      }
    }

    /**
     * Creates a Builder by copying an existing facilityMessage instance
     * @param other The existing instance to copy.
     */
    private Builder(com.maersk.facility.smds.operations.MSK.facilityMessage other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.facility)) {
        this.facility = data().deepCopy(fields()[0].schema(), other.facility);
        fieldSetFlags()[0] = true;
      }
      this.facilityBuilder = null;
    }

    /**
      * Gets the value of the 'facility' field.
      * @return The value.
      */
    public com.maersk.facility.smds.operations.MSK.facility getFacility() {
      return facility;
    }

    /**
      * Sets the value of the 'facility' field.
      * @param value The value of 'facility'.
      * @return This builder.
      */
    public com.maersk.facility.smds.operations.MSK.facilityMessage.Builder setFacility(com.maersk.facility.smds.operations.MSK.facility value) {
      validate(fields()[0], value);
      this.facilityBuilder = null;
      this.facility = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'facility' field has been set.
      * @return True if the 'facility' field has been set, false otherwise.
      */
    public boolean hasFacility() {
      return fieldSetFlags()[0];
    }

    /**
     * Gets the Builder instance for the 'facility' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public com.maersk.facility.smds.operations.MSK.facility.Builder getFacilityBuilder() {
      if (facilityBuilder == null) {
        if (hasFacility()) {
          setFacilityBuilder(com.maersk.facility.smds.operations.MSK.facility.newBuilder(facility));
        } else {
          setFacilityBuilder(com.maersk.facility.smds.operations.MSK.facility.newBuilder());
        }
      }
      return facilityBuilder;
    }

    /**
     * Sets the Builder instance for the 'facility' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public com.maersk.facility.smds.operations.MSK.facilityMessage.Builder setFacilityBuilder(com.maersk.facility.smds.operations.MSK.facility.Builder value) {
      clearFacility();
      facilityBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'facility' field has an active Builder instance
     * @return True if the 'facility' field has an active Builder instance
     */
    public boolean hasFacilityBuilder() {
      return facilityBuilder != null;
    }

    /**
      * Clears the value of the 'facility' field.
      * @return This builder.
      */
    public com.maersk.facility.smds.operations.MSK.facilityMessage.Builder clearFacility() {
      facility = null;
      facilityBuilder = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public facilityMessage build() {
      try {
        facilityMessage record = new facilityMessage();
        if (facilityBuilder != null) {
          record.facility = this.facilityBuilder.build();
        } else {
          record.facility = fieldSetFlags()[0] ? this.facility : (com.maersk.facility.smds.operations.MSK.facility) defaultValue(fields()[0]);
        }
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<facilityMessage>
    WRITER$ = (org.apache.avro.io.DatumWriter<facilityMessage>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<facilityMessage>
    READER$ = (org.apache.avro.io.DatumReader<facilityMessage>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}