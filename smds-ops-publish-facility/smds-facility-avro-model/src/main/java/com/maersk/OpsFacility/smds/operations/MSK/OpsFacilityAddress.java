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
/** Ops Facility Address details */
@org.apache.avro.specific.AvroGenerated
public class OpsFacilityAddress extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 7882234676793904968L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"OpsFacilityAddress\",\"namespace\":\"com.maersk.OpsFacility.smds.operations.MSK\",\"doc\":\"Ops Facility Address details\",\"fields\":[{\"name\":\"HouseNumber\",\"type\":[\"null\",\"string\"]},{\"name\":\"Street\",\"type\":[\"null\",\"string\"]},{\"name\":\"City\",\"type\":[\"null\",\"string\"]},{\"name\":\"PostalCode\",\"type\":[\"null\",\"string\"]},{\"name\":\"PoBox\",\"type\":[\"null\",\"string\"]},{\"name\":\"District\",\"type\":[\"null\",\"string\"]},{\"name\":\"Territory\",\"type\":[\"null\",\"string\"]},{\"name\":\"CountryName\",\"type\":[\"null\",\"string\"]},{\"name\":\"CountryCode\",\"type\":[\"null\",\"string\"]},{\"name\":\"AddressLine2\",\"type\":[\"null\",\"string\"]},{\"name\":\"AddressLine3\",\"type\":[\"null\",\"string\"]},{\"name\":\"Latitude\",\"type\":[\"null\",\"string\"]},{\"name\":\"Longitude\",\"type\":[\"null\",\"string\"]},{\"name\":\"AddressQualityCheckIndicator\",\"type\":[\"null\",\"string\"]}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<OpsFacilityAddress> ENCODER =
      new BinaryMessageEncoder<OpsFacilityAddress>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<OpsFacilityAddress> DECODER =
      new BinaryMessageDecoder<OpsFacilityAddress>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<OpsFacilityAddress> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<OpsFacilityAddress> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<OpsFacilityAddress>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this OpsFacilityAddress to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a OpsFacilityAddress from a ByteBuffer. */
  public static OpsFacilityAddress fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.CharSequence HouseNumber;
  @Deprecated public java.lang.CharSequence Street;
  @Deprecated public java.lang.CharSequence City;
  @Deprecated public java.lang.CharSequence PostalCode;
  @Deprecated public java.lang.CharSequence PoBox;
  @Deprecated public java.lang.CharSequence District;
  @Deprecated public java.lang.CharSequence Territory;
  @Deprecated public java.lang.CharSequence CountryName;
  @Deprecated public java.lang.CharSequence CountryCode;
  @Deprecated public java.lang.CharSequence AddressLine2;
  @Deprecated public java.lang.CharSequence AddressLine3;
  @Deprecated public java.lang.CharSequence Latitude;
  @Deprecated public java.lang.CharSequence Longitude;
  @Deprecated public java.lang.CharSequence AddressQualityCheckIndicator;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public OpsFacilityAddress() {}

  /**
   * All-args constructor.
   * @param HouseNumber The new value for HouseNumber
   * @param Street The new value for Street
   * @param City The new value for City
   * @param PostalCode The new value for PostalCode
   * @param PoBox The new value for PoBox
   * @param District The new value for District
   * @param Territory The new value for Territory
   * @param CountryName The new value for CountryName
   * @param CountryCode The new value for CountryCode
   * @param AddressLine2 The new value for AddressLine2
   * @param AddressLine3 The new value for AddressLine3
   * @param Latitude The new value for Latitude
   * @param Longitude The new value for Longitude
   * @param AddressQualityCheckIndicator The new value for AddressQualityCheckIndicator
   */
  public OpsFacilityAddress(java.lang.CharSequence HouseNumber, java.lang.CharSequence Street, java.lang.CharSequence City, java.lang.CharSequence PostalCode, java.lang.CharSequence PoBox, java.lang.CharSequence District, java.lang.CharSequence Territory, java.lang.CharSequence CountryName, java.lang.CharSequence CountryCode, java.lang.CharSequence AddressLine2, java.lang.CharSequence AddressLine3, java.lang.CharSequence Latitude, java.lang.CharSequence Longitude, java.lang.CharSequence AddressQualityCheckIndicator) {
    this.HouseNumber = HouseNumber;
    this.Street = Street;
    this.City = City;
    this.PostalCode = PostalCode;
    this.PoBox = PoBox;
    this.District = District;
    this.Territory = Territory;
    this.CountryName = CountryName;
    this.CountryCode = CountryCode;
    this.AddressLine2 = AddressLine2;
    this.AddressLine3 = AddressLine3;
    this.Latitude = Latitude;
    this.Longitude = Longitude;
    this.AddressQualityCheckIndicator = AddressQualityCheckIndicator;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return HouseNumber;
    case 1: return Street;
    case 2: return City;
    case 3: return PostalCode;
    case 4: return PoBox;
    case 5: return District;
    case 6: return Territory;
    case 7: return CountryName;
    case 8: return CountryCode;
    case 9: return AddressLine2;
    case 10: return AddressLine3;
    case 11: return Latitude;
    case 12: return Longitude;
    case 13: return AddressQualityCheckIndicator;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: HouseNumber = (java.lang.CharSequence)value$; break;
    case 1: Street = (java.lang.CharSequence)value$; break;
    case 2: City = (java.lang.CharSequence)value$; break;
    case 3: PostalCode = (java.lang.CharSequence)value$; break;
    case 4: PoBox = (java.lang.CharSequence)value$; break;
    case 5: District = (java.lang.CharSequence)value$; break;
    case 6: Territory = (java.lang.CharSequence)value$; break;
    case 7: CountryName = (java.lang.CharSequence)value$; break;
    case 8: CountryCode = (java.lang.CharSequence)value$; break;
    case 9: AddressLine2 = (java.lang.CharSequence)value$; break;
    case 10: AddressLine3 = (java.lang.CharSequence)value$; break;
    case 11: Latitude = (java.lang.CharSequence)value$; break;
    case 12: Longitude = (java.lang.CharSequence)value$; break;
    case 13: AddressQualityCheckIndicator = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'HouseNumber' field.
   * @return The value of the 'HouseNumber' field.
   */
  public java.lang.CharSequence getHouseNumber() {
    return HouseNumber;
  }

  /**
   * Sets the value of the 'HouseNumber' field.
   * @param value the value to set.
   */
  public void setHouseNumber(java.lang.CharSequence value) {
    this.HouseNumber = value;
  }

  /**
   * Gets the value of the 'Street' field.
   * @return The value of the 'Street' field.
   */
  public java.lang.CharSequence getStreet() {
    return Street;
  }

  /**
   * Sets the value of the 'Street' field.
   * @param value the value to set.
   */
  public void setStreet(java.lang.CharSequence value) {
    this.Street = value;
  }

  /**
   * Gets the value of the 'City' field.
   * @return The value of the 'City' field.
   */
  public java.lang.CharSequence getCity() {
    return City;
  }

  /**
   * Sets the value of the 'City' field.
   * @param value the value to set.
   */
  public void setCity(java.lang.CharSequence value) {
    this.City = value;
  }

  /**
   * Gets the value of the 'PostalCode' field.
   * @return The value of the 'PostalCode' field.
   */
  public java.lang.CharSequence getPostalCode() {
    return PostalCode;
  }

  /**
   * Sets the value of the 'PostalCode' field.
   * @param value the value to set.
   */
  public void setPostalCode(java.lang.CharSequence value) {
    this.PostalCode = value;
  }

  /**
   * Gets the value of the 'PoBox' field.
   * @return The value of the 'PoBox' field.
   */
  public java.lang.CharSequence getPoBox() {
    return PoBox;
  }

  /**
   * Sets the value of the 'PoBox' field.
   * @param value the value to set.
   */
  public void setPoBox(java.lang.CharSequence value) {
    this.PoBox = value;
  }

  /**
   * Gets the value of the 'District' field.
   * @return The value of the 'District' field.
   */
  public java.lang.CharSequence getDistrict() {
    return District;
  }

  /**
   * Sets the value of the 'District' field.
   * @param value the value to set.
   */
  public void setDistrict(java.lang.CharSequence value) {
    this.District = value;
  }

  /**
   * Gets the value of the 'Territory' field.
   * @return The value of the 'Territory' field.
   */
  public java.lang.CharSequence getTerritory() {
    return Territory;
  }

  /**
   * Sets the value of the 'Territory' field.
   * @param value the value to set.
   */
  public void setTerritory(java.lang.CharSequence value) {
    this.Territory = value;
  }

  /**
   * Gets the value of the 'CountryName' field.
   * @return The value of the 'CountryName' field.
   */
  public java.lang.CharSequence getCountryName() {
    return CountryName;
  }

  /**
   * Sets the value of the 'CountryName' field.
   * @param value the value to set.
   */
  public void setCountryName(java.lang.CharSequence value) {
    this.CountryName = value;
  }

  /**
   * Gets the value of the 'CountryCode' field.
   * @return The value of the 'CountryCode' field.
   */
  public java.lang.CharSequence getCountryCode() {
    return CountryCode;
  }

  /**
   * Sets the value of the 'CountryCode' field.
   * @param value the value to set.
   */
  public void setCountryCode(java.lang.CharSequence value) {
    this.CountryCode = value;
  }

  /**
   * Gets the value of the 'AddressLine2' field.
   * @return The value of the 'AddressLine2' field.
   */
  public java.lang.CharSequence getAddressLine2() {
    return AddressLine2;
  }

  /**
   * Sets the value of the 'AddressLine2' field.
   * @param value the value to set.
   */
  public void setAddressLine2(java.lang.CharSequence value) {
    this.AddressLine2 = value;
  }

  /**
   * Gets the value of the 'AddressLine3' field.
   * @return The value of the 'AddressLine3' field.
   */
  public java.lang.CharSequence getAddressLine3() {
    return AddressLine3;
  }

  /**
   * Sets the value of the 'AddressLine3' field.
   * @param value the value to set.
   */
  public void setAddressLine3(java.lang.CharSequence value) {
    this.AddressLine3 = value;
  }

  /**
   * Gets the value of the 'Latitude' field.
   * @return The value of the 'Latitude' field.
   */
  public java.lang.CharSequence getLatitude() {
    return Latitude;
  }

  /**
   * Sets the value of the 'Latitude' field.
   * @param value the value to set.
   */
  public void setLatitude(java.lang.CharSequence value) {
    this.Latitude = value;
  }

  /**
   * Gets the value of the 'Longitude' field.
   * @return The value of the 'Longitude' field.
   */
  public java.lang.CharSequence getLongitude() {
    return Longitude;
  }

  /**
   * Sets the value of the 'Longitude' field.
   * @param value the value to set.
   */
  public void setLongitude(java.lang.CharSequence value) {
    this.Longitude = value;
  }

  /**
   * Gets the value of the 'AddressQualityCheckIndicator' field.
   * @return The value of the 'AddressQualityCheckIndicator' field.
   */
  public java.lang.CharSequence getAddressQualityCheckIndicator() {
    return AddressQualityCheckIndicator;
  }

  /**
   * Sets the value of the 'AddressQualityCheckIndicator' field.
   * @param value the value to set.
   */
  public void setAddressQualityCheckIndicator(java.lang.CharSequence value) {
    this.AddressQualityCheckIndicator = value;
  }

  /**
   * Creates a new OpsFacilityAddress RecordBuilder.
   * @return A new OpsFacilityAddress RecordBuilder
   */
  public static com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder newBuilder() {
    return new com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder();
  }

  /**
   * Creates a new OpsFacilityAddress RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new OpsFacilityAddress RecordBuilder
   */
  public static com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder newBuilder(com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder other) {
    return new com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder(other);
  }

  /**
   * Creates a new OpsFacilityAddress RecordBuilder by copying an existing OpsFacilityAddress instance.
   * @param other The existing instance to copy.
   * @return A new OpsFacilityAddress RecordBuilder
   */
  public static com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder newBuilder(com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress other) {
    return new com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder(other);
  }

  /**
   * RecordBuilder for OpsFacilityAddress instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<OpsFacilityAddress>
    implements org.apache.avro.data.RecordBuilder<OpsFacilityAddress> {

    private java.lang.CharSequence HouseNumber;
    private java.lang.CharSequence Street;
    private java.lang.CharSequence City;
    private java.lang.CharSequence PostalCode;
    private java.lang.CharSequence PoBox;
    private java.lang.CharSequence District;
    private java.lang.CharSequence Territory;
    private java.lang.CharSequence CountryName;
    private java.lang.CharSequence CountryCode;
    private java.lang.CharSequence AddressLine2;
    private java.lang.CharSequence AddressLine3;
    private java.lang.CharSequence Latitude;
    private java.lang.CharSequence Longitude;
    private java.lang.CharSequence AddressQualityCheckIndicator;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.HouseNumber)) {
        this.HouseNumber = data().deepCopy(fields()[0].schema(), other.HouseNumber);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.Street)) {
        this.Street = data().deepCopy(fields()[1].schema(), other.Street);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.City)) {
        this.City = data().deepCopy(fields()[2].schema(), other.City);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.PostalCode)) {
        this.PostalCode = data().deepCopy(fields()[3].schema(), other.PostalCode);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.PoBox)) {
        this.PoBox = data().deepCopy(fields()[4].schema(), other.PoBox);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.District)) {
        this.District = data().deepCopy(fields()[5].schema(), other.District);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.Territory)) {
        this.Territory = data().deepCopy(fields()[6].schema(), other.Territory);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.CountryName)) {
        this.CountryName = data().deepCopy(fields()[7].schema(), other.CountryName);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.CountryCode)) {
        this.CountryCode = data().deepCopy(fields()[8].schema(), other.CountryCode);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.AddressLine2)) {
        this.AddressLine2 = data().deepCopy(fields()[9].schema(), other.AddressLine2);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.AddressLine3)) {
        this.AddressLine3 = data().deepCopy(fields()[10].schema(), other.AddressLine3);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.Latitude)) {
        this.Latitude = data().deepCopy(fields()[11].schema(), other.Latitude);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.Longitude)) {
        this.Longitude = data().deepCopy(fields()[12].schema(), other.Longitude);
        fieldSetFlags()[12] = true;
      }
      if (isValidValue(fields()[13], other.AddressQualityCheckIndicator)) {
        this.AddressQualityCheckIndicator = data().deepCopy(fields()[13].schema(), other.AddressQualityCheckIndicator);
        fieldSetFlags()[13] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing OpsFacilityAddress instance
     * @param other The existing instance to copy.
     */
    private Builder(com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.HouseNumber)) {
        this.HouseNumber = data().deepCopy(fields()[0].schema(), other.HouseNumber);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.Street)) {
        this.Street = data().deepCopy(fields()[1].schema(), other.Street);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.City)) {
        this.City = data().deepCopy(fields()[2].schema(), other.City);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.PostalCode)) {
        this.PostalCode = data().deepCopy(fields()[3].schema(), other.PostalCode);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.PoBox)) {
        this.PoBox = data().deepCopy(fields()[4].schema(), other.PoBox);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.District)) {
        this.District = data().deepCopy(fields()[5].schema(), other.District);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.Territory)) {
        this.Territory = data().deepCopy(fields()[6].schema(), other.Territory);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.CountryName)) {
        this.CountryName = data().deepCopy(fields()[7].schema(), other.CountryName);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.CountryCode)) {
        this.CountryCode = data().deepCopy(fields()[8].schema(), other.CountryCode);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.AddressLine2)) {
        this.AddressLine2 = data().deepCopy(fields()[9].schema(), other.AddressLine2);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.AddressLine3)) {
        this.AddressLine3 = data().deepCopy(fields()[10].schema(), other.AddressLine3);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.Latitude)) {
        this.Latitude = data().deepCopy(fields()[11].schema(), other.Latitude);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.Longitude)) {
        this.Longitude = data().deepCopy(fields()[12].schema(), other.Longitude);
        fieldSetFlags()[12] = true;
      }
      if (isValidValue(fields()[13], other.AddressQualityCheckIndicator)) {
        this.AddressQualityCheckIndicator = data().deepCopy(fields()[13].schema(), other.AddressQualityCheckIndicator);
        fieldSetFlags()[13] = true;
      }
    }

    /**
      * Gets the value of the 'HouseNumber' field.
      * @return The value.
      */
    public java.lang.CharSequence getHouseNumber() {
      return HouseNumber;
    }

    /**
      * Sets the value of the 'HouseNumber' field.
      * @param value The value of 'HouseNumber'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder setHouseNumber(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.HouseNumber = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'HouseNumber' field has been set.
      * @return True if the 'HouseNumber' field has been set, false otherwise.
      */
    public boolean hasHouseNumber() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'HouseNumber' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder clearHouseNumber() {
      HouseNumber = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'Street' field.
      * @return The value.
      */
    public java.lang.CharSequence getStreet() {
      return Street;
    }

    /**
      * Sets the value of the 'Street' field.
      * @param value The value of 'Street'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder setStreet(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.Street = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'Street' field has been set.
      * @return True if the 'Street' field has been set, false otherwise.
      */
    public boolean hasStreet() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'Street' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder clearStreet() {
      Street = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'City' field.
      * @return The value.
      */
    public java.lang.CharSequence getCity() {
      return City;
    }

    /**
      * Sets the value of the 'City' field.
      * @param value The value of 'City'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder setCity(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.City = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'City' field has been set.
      * @return True if the 'City' field has been set, false otherwise.
      */
    public boolean hasCity() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'City' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder clearCity() {
      City = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'PostalCode' field.
      * @return The value.
      */
    public java.lang.CharSequence getPostalCode() {
      return PostalCode;
    }

    /**
      * Sets the value of the 'PostalCode' field.
      * @param value The value of 'PostalCode'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder setPostalCode(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.PostalCode = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'PostalCode' field has been set.
      * @return True if the 'PostalCode' field has been set, false otherwise.
      */
    public boolean hasPostalCode() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'PostalCode' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder clearPostalCode() {
      PostalCode = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'PoBox' field.
      * @return The value.
      */
    public java.lang.CharSequence getPoBox() {
      return PoBox;
    }

    /**
      * Sets the value of the 'PoBox' field.
      * @param value The value of 'PoBox'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder setPoBox(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.PoBox = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'PoBox' field has been set.
      * @return True if the 'PoBox' field has been set, false otherwise.
      */
    public boolean hasPoBox() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'PoBox' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder clearPoBox() {
      PoBox = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'District' field.
      * @return The value.
      */
    public java.lang.CharSequence getDistrict() {
      return District;
    }

    /**
      * Sets the value of the 'District' field.
      * @param value The value of 'District'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder setDistrict(java.lang.CharSequence value) {
      validate(fields()[5], value);
      this.District = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'District' field has been set.
      * @return True if the 'District' field has been set, false otherwise.
      */
    public boolean hasDistrict() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'District' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder clearDistrict() {
      District = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'Territory' field.
      * @return The value.
      */
    public java.lang.CharSequence getTerritory() {
      return Territory;
    }

    /**
      * Sets the value of the 'Territory' field.
      * @param value The value of 'Territory'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder setTerritory(java.lang.CharSequence value) {
      validate(fields()[6], value);
      this.Territory = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'Territory' field has been set.
      * @return True if the 'Territory' field has been set, false otherwise.
      */
    public boolean hasTerritory() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'Territory' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder clearTerritory() {
      Territory = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'CountryName' field.
      * @return The value.
      */
    public java.lang.CharSequence getCountryName() {
      return CountryName;
    }

    /**
      * Sets the value of the 'CountryName' field.
      * @param value The value of 'CountryName'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder setCountryName(java.lang.CharSequence value) {
      validate(fields()[7], value);
      this.CountryName = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'CountryName' field has been set.
      * @return True if the 'CountryName' field has been set, false otherwise.
      */
    public boolean hasCountryName() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'CountryName' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder clearCountryName() {
      CountryName = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /**
      * Gets the value of the 'CountryCode' field.
      * @return The value.
      */
    public java.lang.CharSequence getCountryCode() {
      return CountryCode;
    }

    /**
      * Sets the value of the 'CountryCode' field.
      * @param value The value of 'CountryCode'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder setCountryCode(java.lang.CharSequence value) {
      validate(fields()[8], value);
      this.CountryCode = value;
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
      * Checks whether the 'CountryCode' field has been set.
      * @return True if the 'CountryCode' field has been set, false otherwise.
      */
    public boolean hasCountryCode() {
      return fieldSetFlags()[8];
    }


    /**
      * Clears the value of the 'CountryCode' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder clearCountryCode() {
      CountryCode = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    /**
      * Gets the value of the 'AddressLine2' field.
      * @return The value.
      */
    public java.lang.CharSequence getAddressLine2() {
      return AddressLine2;
    }

    /**
      * Sets the value of the 'AddressLine2' field.
      * @param value The value of 'AddressLine2'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder setAddressLine2(java.lang.CharSequence value) {
      validate(fields()[9], value);
      this.AddressLine2 = value;
      fieldSetFlags()[9] = true;
      return this;
    }

    /**
      * Checks whether the 'AddressLine2' field has been set.
      * @return True if the 'AddressLine2' field has been set, false otherwise.
      */
    public boolean hasAddressLine2() {
      return fieldSetFlags()[9];
    }


    /**
      * Clears the value of the 'AddressLine2' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder clearAddressLine2() {
      AddressLine2 = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    /**
      * Gets the value of the 'AddressLine3' field.
      * @return The value.
      */
    public java.lang.CharSequence getAddressLine3() {
      return AddressLine3;
    }

    /**
      * Sets the value of the 'AddressLine3' field.
      * @param value The value of 'AddressLine3'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder setAddressLine3(java.lang.CharSequence value) {
      validate(fields()[10], value);
      this.AddressLine3 = value;
      fieldSetFlags()[10] = true;
      return this;
    }

    /**
      * Checks whether the 'AddressLine3' field has been set.
      * @return True if the 'AddressLine3' field has been set, false otherwise.
      */
    public boolean hasAddressLine3() {
      return fieldSetFlags()[10];
    }


    /**
      * Clears the value of the 'AddressLine3' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder clearAddressLine3() {
      AddressLine3 = null;
      fieldSetFlags()[10] = false;
      return this;
    }

    /**
      * Gets the value of the 'Latitude' field.
      * @return The value.
      */
    public java.lang.CharSequence getLatitude() {
      return Latitude;
    }

    /**
      * Sets the value of the 'Latitude' field.
      * @param value The value of 'Latitude'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder setLatitude(java.lang.CharSequence value) {
      validate(fields()[11], value);
      this.Latitude = value;
      fieldSetFlags()[11] = true;
      return this;
    }

    /**
      * Checks whether the 'Latitude' field has been set.
      * @return True if the 'Latitude' field has been set, false otherwise.
      */
    public boolean hasLatitude() {
      return fieldSetFlags()[11];
    }


    /**
      * Clears the value of the 'Latitude' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder clearLatitude() {
      Latitude = null;
      fieldSetFlags()[11] = false;
      return this;
    }

    /**
      * Gets the value of the 'Longitude' field.
      * @return The value.
      */
    public java.lang.CharSequence getLongitude() {
      return Longitude;
    }

    /**
      * Sets the value of the 'Longitude' field.
      * @param value The value of 'Longitude'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder setLongitude(java.lang.CharSequence value) {
      validate(fields()[12], value);
      this.Longitude = value;
      fieldSetFlags()[12] = true;
      return this;
    }

    /**
      * Checks whether the 'Longitude' field has been set.
      * @return True if the 'Longitude' field has been set, false otherwise.
      */
    public boolean hasLongitude() {
      return fieldSetFlags()[12];
    }


    /**
      * Clears the value of the 'Longitude' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder clearLongitude() {
      Longitude = null;
      fieldSetFlags()[12] = false;
      return this;
    }

    /**
      * Gets the value of the 'AddressQualityCheckIndicator' field.
      * @return The value.
      */
    public java.lang.CharSequence getAddressQualityCheckIndicator() {
      return AddressQualityCheckIndicator;
    }

    /**
      * Sets the value of the 'AddressQualityCheckIndicator' field.
      * @param value The value of 'AddressQualityCheckIndicator'.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder setAddressQualityCheckIndicator(java.lang.CharSequence value) {
      validate(fields()[13], value);
      this.AddressQualityCheckIndicator = value;
      fieldSetFlags()[13] = true;
      return this;
    }

    /**
      * Checks whether the 'AddressQualityCheckIndicator' field has been set.
      * @return True if the 'AddressQualityCheckIndicator' field has been set, false otherwise.
      */
    public boolean hasAddressQualityCheckIndicator() {
      return fieldSetFlags()[13];
    }


    /**
      * Clears the value of the 'AddressQualityCheckIndicator' field.
      * @return This builder.
      */
    public com.maersk.OpsFacility.smds.operations.MSK.OpsFacilityAddress.Builder clearAddressQualityCheckIndicator() {
      AddressQualityCheckIndicator = null;
      fieldSetFlags()[13] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public OpsFacilityAddress build() {
      try {
        OpsFacilityAddress record = new OpsFacilityAddress();
        record.HouseNumber = fieldSetFlags()[0] ? this.HouseNumber : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.Street = fieldSetFlags()[1] ? this.Street : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.City = fieldSetFlags()[2] ? this.City : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.PostalCode = fieldSetFlags()[3] ? this.PostalCode : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.PoBox = fieldSetFlags()[4] ? this.PoBox : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.District = fieldSetFlags()[5] ? this.District : (java.lang.CharSequence) defaultValue(fields()[5]);
        record.Territory = fieldSetFlags()[6] ? this.Territory : (java.lang.CharSequence) defaultValue(fields()[6]);
        record.CountryName = fieldSetFlags()[7] ? this.CountryName : (java.lang.CharSequence) defaultValue(fields()[7]);
        record.CountryCode = fieldSetFlags()[8] ? this.CountryCode : (java.lang.CharSequence) defaultValue(fields()[8]);
        record.AddressLine2 = fieldSetFlags()[9] ? this.AddressLine2 : (java.lang.CharSequence) defaultValue(fields()[9]);
        record.AddressLine3 = fieldSetFlags()[10] ? this.AddressLine3 : (java.lang.CharSequence) defaultValue(fields()[10]);
        record.Latitude = fieldSetFlags()[11] ? this.Latitude : (java.lang.CharSequence) defaultValue(fields()[11]);
        record.Longitude = fieldSetFlags()[12] ? this.Longitude : (java.lang.CharSequence) defaultValue(fields()[12]);
        record.AddressQualityCheckIndicator = fieldSetFlags()[13] ? this.AddressQualityCheckIndicator : (java.lang.CharSequence) defaultValue(fields()[13]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<OpsFacilityAddress>
    WRITER$ = (org.apache.avro.io.DatumWriter<OpsFacilityAddress>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<OpsFacilityAddress>
    READER$ = (org.apache.avro.io.DatumReader<OpsFacilityAddress>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
