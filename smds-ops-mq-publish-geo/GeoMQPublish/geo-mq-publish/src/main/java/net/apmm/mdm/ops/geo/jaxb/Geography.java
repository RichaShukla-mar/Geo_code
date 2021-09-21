package net.apmm.mdm.ops.geo.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="Geography")
public class Geography {

    @XmlElement(name = "City")
    protected Geography.City  city;

    public void setCity(City city) {
        this.city = city;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "name",
            "status",
            "validFrom",
            "validTo",
            "longitude",
            "latitude",
            "timeZone",
            "daylightSavingTime",
            "utOffSetMinutes", ///Richa change
            "daylightSavingStart",
            "daylightSavingEnd",
            "daylightSavingShiftMinutes",
            "description",
            "workaroundReason",
            "portFlag",
            "olsonTimezone",
            "alternateNames",
            "alternateCodes",
            "country",
            "parent",
            "subCityParent",
            "bda"
    })

    public static class City {

        @XmlElement(name = "Name", required = true)
        protected String name;
        @XmlElement(name = "Status", required = true)
        protected String status;
        @XmlElement(name = "ValidFrom", required = true)
        @XmlSchemaType(name = "date")
        protected String validFrom;
        @XmlElement(name = "ValidTo", required = true)
        @XmlSchemaType(name = "date")
        protected String validTo;
        @XmlElement(name = "Longitude", required = true)
        protected String longitude;
        @XmlElement(name = "Latitude", required = true)
        protected String latitude;
        @XmlElement(name = "TimeZone")
        protected String timeZone;
        @XmlElement(name = "DaylightSavingTime")
        protected String daylightSavingTime;
        @XmlElement(name = "UTCOffsetMinutes") //Richa Change
        protected String utOffSetMinutes;
        public String getUtOffSetMinutes() {
            return utOffSetMinutes;
        }

        public void setUtOffSetMinutes(String utOffSetMinutes) {
            this.utOffSetMinutes = utOffSetMinutes;
        }


        @XmlElement(name = "DaylightSavingStart")
        protected String daylightSavingStart;
        @XmlElement(name = "DaylightSavingEnd")
        protected String daylightSavingEnd;
        @XmlElement(name = "DaylightSavingShiftMinutes")
        protected String daylightSavingShiftMinutes;
        @XmlElement(name = "Description")
        protected String description;
        @XmlElement(name = "WorkaroundReason")
        protected String workaroundReason;
        @XmlElement(name = "PortFlag", required = true)
        protected String portFlag;
        @XmlElement(name = "OlsonTimezone")
        protected String olsonTimezone;
        @XmlElement(name = "AlternateNames")
        protected Geography.City.AlternateNames alternateNames;
        @XmlElement(name = "AlternateCodes", required = true)
        protected Geography.City.AlternateCodes alternateCodes;
        @XmlElement(name = "Country")
        protected Geography.City.Country country;
        @XmlElement(name = "Parent", required = true)
        protected Geography.City.Parent parent;
        @XmlElement(name = "SubCityParent")
        protected Geography.City.SubCityParent subCityParent;
        @XmlElement(name = "BDA")
        protected Geography.City.BDA bda;

        /**
         * Gets the value of the name property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the status property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setStatus(String value) {
            this.status = value;
        }

        /**
         * Gets the value of the validFrom property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getValidFrom() {
            return validFrom;
        }

        /**
         * Sets the value of the validFrom property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setValidFrom(String value) {
            this.validFrom = value;
        }

        /**
         * Gets the value of the validTo property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getValidTo() {
            return validTo;
        }

        /**
         * Sets the value of the validTo property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setValidTo(String value) {
            this.validTo = value;
        }

        /**
         * Gets the value of the longitude property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getLongitude() {
            return longitude;
        }

        /**
         * Sets the value of the longitude property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setLongitude(String value) {
            this.longitude = value;
        }

        /**
         * Gets the value of the latitude property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getLatitude() {
            return latitude;
        }

        /**
         * Sets the value of the latitude property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setLatitude(String value) {
            this.latitude = value;
        }

        /**
         * Gets the value of the timeZone property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getTimeZone() {
            return timeZone;
        }

        /**
         * Sets the value of the timeZone property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setTimeZone(String value) {
            this.timeZone = value;
        }

        /**
         * Gets the value of the daylightSavingTime property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getDaylightSavingTime() {
            return daylightSavingTime;
        }

        /**
         * Sets the value of the daylightSavingTime property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setDaylightSavingTime(String value) {
            this.daylightSavingTime = value;
        }

        /* *//**
         * Gets the value of the utcOffsetMinutes property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getUTCOffsetMinutes() {
            return utOffSetMinutes;
        }

        /**
         * Sets the value of the utcOffsetMinutes property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setUTCOffsetMinutes(String value) {
            this.utOffSetMinutes = value;
        }

        /**
         * Gets the value of the daylightSavingStart property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getDaylightSavingStart() {
            return daylightSavingStart;
        }

        /**
         * Sets the value of the daylightSavingStart property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setDaylightSavingStart(String value) {
            this.daylightSavingStart = value;
        }

        /**
         * Gets the value of the daylightSavingEnd property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getDaylightSavingEnd() {
            return daylightSavingEnd;
        }

        /**
         * Sets the value of the daylightSavingEnd property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setDaylightSavingEnd(String value) {
            this.daylightSavingEnd = value;
        }

        /**
         * Gets the value of the daylightSavingShiftMinutes property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getDaylightSavingShiftMinutes() {
            return daylightSavingShiftMinutes;
        }

        /**
         * Sets the value of the daylightSavingShiftMinutes property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setDaylightSavingShiftMinutes(String value) {
            this.daylightSavingShiftMinutes = value;
        }

        /**
         * Gets the value of the description property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the value of the description property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setDescription(String value) {
            this.description = value;
        }

        /**
         * Gets the value of the workaroundReason property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getWorkaroundReason() {
            return workaroundReason;
        }

        /**
         * Sets the value of the workaroundReason property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setWorkaroundReason(String value) {
            this.workaroundReason = value;
        }

        /**
         * Gets the value of the portFlag property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getPortFlag() {
            return portFlag;
        }

        /**
         * Sets the value of the portFlag property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setPortFlag(String value) {
            this.portFlag = value;
        }

        /**
         * Gets the value of the olsonTimezone property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getOlsonTimezone() {
            return olsonTimezone;
        }

        /**
         * Sets the value of the olsonTimezone property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setOlsonTimezone(String value) {
            this.olsonTimezone = value;
        }

        /**
         * Gets the value of the alternateNames property.
         *
         * @return
         *     possible object is
         *     {@link Geography.City.AlternateNames }
         *
         */
        public Geography.City.AlternateNames getAlternateNames() {
            return alternateNames;
        }

        /**
         * Sets the value of the alternateNames property.
         *
         * @param value
         *     allowed object is
         *     {@link Geography.City.AlternateNames }
         *
         */
        public void setAlternateNames(Geography.City.AlternateNames value) {
            this.alternateNames = value;
        }

        /**
         * Gets the value of the alternateCodes property.
         *
         * @return
         *     possible object is
         *     {@link Geography.City.AlternateCodes }
         *
         */
        public Geography.City.AlternateCodes getAlternateCodes() {
            return alternateCodes;
        }

        /**
         * Sets the value of the alternateCodes property.
         *
         * @param value
         *     allowed object is
         *     {@link Geography.City.AlternateCodes }
         *
         */
        public void setAlternateCodes(Geography.City.AlternateCodes value) {
            this.alternateCodes = value;
        }

        /**
         * Gets the value of the country property.
         *
         * @return
         *     possible object is
         *     {@link Geography.City.Country }
         *
         */
        public Geography.City.Country getCountry() {
            return country;
        }

        /**
         * Sets the value of the country property.
         *
         * @param value
         *     allowed object is
         *     {@link Geography.City.Country }
         *
         */
        public void setCountry(Geography.City.Country value) {
            this.country = value;
        }

        /**
         * Gets the value of the parent property.
         *
         * @return
         *     possible object is
         *     {@link Geography.City.Parent }
         *
         */
        public Geography.City.Parent getParent() {
            return parent;
        }

        /**
         * Sets the value of the parent property.
         *
         * @param value
         *     allowed object is
         *     {@link Geography.City.Parent }
         *
         */
        public void setParent(Geography.City.Parent value) {
            this.parent = value;
        }

        /**
         * Gets the value of the subCityParent property.
         *
         * @return
         *     possible object is
         *     {@link Geography.City.SubCityParent }
         *
         */
        public Geography.City.SubCityParent getSubCityParent() {
            return subCityParent;
        }

        /**
         * Sets the value of the subCityParent property.
         *
         * @param value
         *     allowed object is
         *     {@link Geography.City.SubCityParent }
         *
         */
        public void setSubCityParent(Geography.City.SubCityParent value) {
            this.subCityParent = value;
        }

        /**
         * Gets the value of the bda property.
         *
         * @return
         *     possible object is
         *     {@link Geography.City.BDA }
         *
         */
        public Geography.City.BDA getBDA() {
            return bda;
        }

        /**
         * Sets the value of the bda property.
         *
         * @param value
         *     allowed object is
         *     {@link Geography.City.BDA }
         *
         */
        public void setBDA(Geography.City.BDA value) {
            this.bda = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="AlternateCode" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "alternateCode"
        })
        public static class AlternateCodes {

            @XmlElement(name = "AlternateCode", required = true)
            protected List<Geography.City.AlternateCodes.AlternateCode> alternateCode;

            /**
             * Gets the value of the alternateCode property.
             *
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the alternateCode property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAlternateCode().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Geography.City.AlternateCodes.AlternateCode }
             *
             *
             */
            public List<Geography.City.AlternateCodes.AlternateCode> getAlternateCode() {
                if (alternateCode == null) {
                    alternateCode = new ArrayList<Geography.City.AlternateCodes.AlternateCode>();
                }
                return this.alternateCode;
            }


            /**
             * <p>Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this class.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "codeType",
                    "code"
            })
            public static class AlternateCode {

                @XmlElement(name = "CodeType", required = true)
                protected String codeType;
                @XmlElement(name = "Code", required = true)
                protected String code;

                /**
                 * Gets the value of the codeType property.
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getCodeType() {
                    return codeType;
                }

                /**
                 * Sets the value of the codeType property.
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setCodeType(String value) {
                    this.codeType = value;
                }

                /**
                 * Gets the value of the code property.
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getCode() {
                    return code;
                }

                /**
                 * Sets the value of the code property.
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setCode(String value) {
                    this.code = value;
                }

            }

            /*******Richa Added********/
            /**
             * @param alternateCode the alternateCode to set
             */
            public void setAlternateCode(List<Geography.City.AlternateCodes.AlternateCode> alternateCode) {
                this.alternateCode = alternateCode;
            }
            /*******Richa Added********/

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="AlternateName" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "alternateName"
        })
        public static class AlternateNames {

            @XmlElement(name = "AlternateName", required = true)
            protected List<Geography.City.AlternateNames.AlternateName> alternateName;

            /**
             * Gets the value of the alternateName property.
             *
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the alternateName property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAlternateName().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Geography.City.AlternateNames.AlternateName }
             *
             *
             */
            public List<Geography.City.AlternateNames.AlternateName> getAlternateName() {
                if (alternateName == null) {
                    alternateName = new ArrayList<Geography.City.AlternateNames.AlternateName>();
                }
                return this.alternateName;
            }


            /**
             * <p>Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this class.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "name",
                    "description",
                    "status"
            })
            public static class AlternateName {

                @XmlElement(name = "Name", required = true)
                protected String name;
                @XmlElement(name = "Description")
                protected String description;
                @XmlElement(name = "Status", required = true)
                protected String status;

                /**
                 * Gets the value of the name property.
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getName() {
                    return name;
                }

                /**
                 * Sets the value of the name property.
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setName(String value) {
                    this.name = value;
                }

                /**
                 * Gets the value of the description property.
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getDescription() {
                    return description;
                }

                /**
                 * Sets the value of the description property.
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setDescription(String value) {
                    this.description = value;
                }

                /**
                 * Gets the value of the status property.
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getStatus() {
                    return status;
                }

                /**
                 * Sets the value of the status property.
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setStatus(String value) {
                    this.status = value;
                }

            }
            /*******Richa Added *********/

            /**
             * @param alternateName the alternateName to set
             */
            public void setAlternateName(List<Geography.City.AlternateNames.AlternateName> alternateName) {
                this.alternateName = alternateName;
            }

            /*******Richa Added *********/

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="BDAType" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="AlternateCodes">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="AlternateCode" maxOccurs="unbounded">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                       &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "bdaType"
        })
        public static class BDA {

            @XmlElement(name = "BDAType", required = true)
            protected List<Geography.City.BDA.BDAType> bdaType;

            /**
             * Gets the value of the bdaType property.
             *
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the bdaType property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBDAType().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Geography.City.BDA.BDAType }
             *
             *
             */
            public List<Geography.City.BDA.BDAType> getBDAType() {
                if (bdaType == null) {
                    bdaType = new ArrayList<Geography.City.BDA.BDAType>();
                }
                return this.bdaType;
            }


            /**
             * <p>Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this class.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="AlternateCodes">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="AlternateCode" maxOccurs="unbounded">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                             &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "name",
                    "type",
                    "alternateCodes"
            })
            public static class BDAType {

                @XmlElement(name = "Name", required = true)
                protected String name;
                @XmlElement(name = "Type", required = true)
                protected String type;
                @XmlElement(name = "AlternateCodes", required = true)
                protected Geography.City.BDA.BDAType.AlternateCodes alternateCodes;

                /**
                 * Gets the value of the name property.
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getName() {
                    return name;
                }

                /**
                 * Sets the value of the name property.
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setName(String value) {
                    this.name = value;
                }

                /**
                 * Gets the value of the type property.
                 *
                 * @return
                 *     possible object is
                 *     {@link String }
                 *
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Sets the value of the type property.
                 *
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *
                 */
                public void setType(String value) {
                    this.type = value;
                }

                /**
                 * Gets the value of the alternateCodes property.
                 *
                 * @return
                 *     possible object is
                 *     {@link Geography.City.BDA.BDAType.AlternateCodes }
                 *
                 */
                public Geography.City.BDA.BDAType.AlternateCodes getAlternateCodes() {
                    return alternateCodes;
                }

                /**
                 * Sets the value of the alternateCodes property.
                 *
                 * @param value
                 *     allowed object is
                 *     {@link Geography.City.BDA.BDAType.AlternateCodes }
                 *
                 */
                public void setAlternateCodes(Geography.City.BDA.BDAType.AlternateCodes value) {
                    this.alternateCodes = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 *
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="AlternateCode" maxOccurs="unbounded">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                   &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "alternateCode"
                })
                public static class AlternateCodes {

                    @XmlElement(name = "AlternateCode", required = true)
                    protected List<Geography.City.BDA.BDAType.AlternateCodes.AlternateCode> alternateCode;

                    /**
                     * Gets the value of the alternateCode property.
                     *
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the alternateCode property.
                     *
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getAlternateCode().add(newItem);
                     * </pre>
                     *
                     *
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link Geography.City.BDA.BDAType.AlternateCodes.AlternateCode }
                     *
                     *
                     */
                    public List<Geography.City.BDA.BDAType.AlternateCodes.AlternateCode> getAlternateCode() {
                        if (alternateCode == null) {
                            alternateCode = new ArrayList<Geography.City.BDA.BDAType.AlternateCodes.AlternateCode>();
                        }
                        return this.alternateCode;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     *
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     *
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     *
                     *
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "codeType",
                            "code"
                    })
                    public static class AlternateCode {

                        @XmlElement(name = "CodeType", required = true)
                        protected String codeType;
                        @XmlElement(name = "Code", required = true)
                        protected String code;

                        /**
                         * Gets the value of the codeType property.
                         *
                         * @return
                         *     possible object is
                         *     {@link String }
                         *
                         */
                        public String getCodeType() {
                            return codeType;
                        }

                        /**
                         * Sets the value of the codeType property.
                         *
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *
                         */
                        public void setCodeType(String value) {
                            this.codeType = value;
                        }

                        /**
                         * Gets the value of the code property.
                         *
                         * @return
                         *     possible object is
                         *     {@link String }
                         *
                         */
                        public String getCode() {
                            return code;
                        }

                        /**
                         * Sets the value of the code property.
                         *
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *
                         */
                        public void setCode(String value) {
                            this.code = value;
                        }

                    }

                    /********Richa Added******/
                    /**
                     * @param alternateCode the alternateCode to set
                     */
                    public void setAlternateCode(List<Geography.City.BDA.BDAType.AlternateCodes.AlternateCode> alternateCode) {
                        this.alternateCode = alternateCode;
                    }
                    /********Richa Added******/

                }

            }
            /********Richa Added******/
            /**
             * @return the bdaType
             */
            public List<Geography.City.BDA.BDAType> getBdaType() {
                return bdaType;
            }
            /**
             * @param bdaType the bdaType to set
             */
            public void setBdaType(List<Geography.City.BDA.BDAType> bdaType) {
                this.bdaType = bdaType;
            }
            /********Richa Added******/
        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="AlternateCodes">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="AlternateCode" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "name",
                "alternateCodes"
        })
        public static class Country {

            @XmlElement(name = "Name", required = true)
            protected String name;
            @XmlElement(name = "AlternateCodes", required = true)
            protected Geography.City.Country.AlternateCodes alternateCodes;

            /**
             * Gets the value of the name property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Gets the value of the alternateCodes property.
             *
             * @return
             *     possible object is
             *     {@link Geography.City.Country.AlternateCodes }
             *
             */
            public Geography.City.Country.AlternateCodes getAlternateCodes() {
                return alternateCodes;
            }

            /**
             * Sets the value of the alternateCodes property.
             *
             * @param value
             *     allowed object is
             *     {@link Geography.City.Country.AlternateCodes }
             *
             */
            public void setAlternateCodes(Geography.City.Country.AlternateCodes value) {
                this.alternateCodes = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this class.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="AlternateCode" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "alternateCode"
            })
            public static class AlternateCodes {

                @XmlElement(name = "AlternateCode", required = true)
                protected List<Geography.City.Country.AlternateCodes.AlternateCode> alternateCode;

                /**
                 * Gets the value of the alternateCode property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the alternateCode property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getAlternateCode().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Geography.City.Country.AlternateCodes.AlternateCode }
                 *
                 *
                 */
                public List<Geography.City.Country.AlternateCodes.AlternateCode> getAlternateCode() {
                    if (alternateCode == null) {
                        alternateCode = new ArrayList<Geography.City.Country.AlternateCodes.AlternateCode>();
                    }
                    return this.alternateCode;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 *
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "codeType",
                        "code"
                })
                public static class AlternateCode {

                    @XmlElement(name = "CodeType", required = true)
                    protected String codeType;
                    @XmlElement(name = "Code", required = true)
                    protected String code;

                    /**
                     * Gets the value of the codeType property.
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCodeType() {
                        return codeType;
                    }

                    /**
                     * Sets the value of the codeType property.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCodeType(String value) {
                        this.codeType = value;
                    }

                    /**
                     * Gets the value of the code property.
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCode() {
                        return code;
                    }

                    /**
                     * Sets the value of the code property.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCode(String value) {
                        this.code = value;
                    }

                }
                /*****Richa Added******/

                /**
                 * @param alternateCode the alternateCode to set
                 */
                public void setAlternateCode(List<Geography.City.Country.AlternateCodes.AlternateCode> alternateCode) {
                    this.alternateCode = alternateCode;
                }
                /*****Richa Added******/

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="AlternateCodes">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="AlternateCode" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "name",
                "type",
                "alternateCodes"
        })
        public static class Parent {

            @XmlElement(name = "Name", required = true)
            protected String name;
            @XmlElement(name = "Type", required = true)
            protected String type;
            @XmlElement(name = "AlternateCodes", required = true)
            protected Geography.City.Parent.AlternateCodes alternateCodes;

            /**
             * Gets the value of the name property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Gets the value of the type property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setType(String value) {
                this.type = value;
            }

            /**
             * Gets the value of the alternateCodes property.
             *
             * @return
             *     possible object is
             *     {@link Geography.City.Parent.AlternateCodes }
             *
             */
            public Geography.City.Parent.AlternateCodes getAlternateCodes() {
                return alternateCodes;
            }

            /**
             * Sets the value of the alternateCodes property.
             *
             * @param value
             *     allowed object is
             *     {@link Geography.City.Parent.AlternateCodes }
             *
             */
            public void setAlternateCodes(Geography.City.Parent.AlternateCodes value) {
                this.alternateCodes = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this class.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="AlternateCode" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "alternateCode"
            })
            public static class AlternateCodes {

                @XmlElement(name = "AlternateCode", required = true)
                protected List<Geography.City.Parent.AlternateCodes.AlternateCode> alternateCode;

                /**
                 * Gets the value of the alternateCode property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the alternateCode property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getAlternateCode().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Geography.City.Parent.AlternateCodes.AlternateCode }
                 *
                 *
                 */
                public List<Geography.City.Parent.AlternateCodes.AlternateCode> getAlternateCode() {
                    if (alternateCode == null) {
                        alternateCode = new ArrayList<Geography.City.Parent.AlternateCodes.AlternateCode>();
                    }
                    return this.alternateCode;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 *
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "codeType",
                        "code"
                })
                public static class AlternateCode {

                    @XmlElement(name = "CodeType", required = true)
                    protected String codeType;
                    @XmlElement(name = "Code", required = true)
                    protected String code;

                    /**
                     * Gets the value of the codeType property.
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCodeType() {
                        return codeType;
                    }

                    /**
                     * Sets the value of the codeType property.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCodeType(String value) {
                        this.codeType = value;
                    }

                    /**
                     * Gets the value of the code property.
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCode() {
                        return code;
                    }

                    /**
                     * Sets the value of the code property.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCode(String value) {
                        this.code = value;
                    }

                }
                /******Richa Added ******/
                /**
                 * @param alternateCode the alternateCode to set
                 */
                public void setAlternateCode(List<Geography.City.Parent.AlternateCodes.AlternateCode> alternateCode) {
                    this.alternateCode = alternateCode;
                }
                /******Richa Added ******/

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="AlternateCodes">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="AlternateCode" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "name",
                "type",
                "alternateCodes"
        })
        public static class SubCityParent {

            @XmlElement(name = "Name", required = true)
            protected String name;
            @XmlElement(name = "Type", required = true)
            protected String type;
            @XmlElement(name = "AlternateCodes", required = true)
            protected Geography.City.SubCityParent.AlternateCodes alternateCodes;

            /**
             * Gets the value of the name property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Gets the value of the type property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setType(String value) {
                this.type = value;
            }

            /**
             * Gets the value of the alternateCodes property.
             *
             * @return
             *     possible object is
             *     {@link Geography.City.SubCityParent.AlternateCodes }
             *
             */
            public Geography.City.SubCityParent.AlternateCodes getAlternateCodes() {
                return alternateCodes;
            }

            /**
             * Sets the value of the alternateCodes property.
             *
             * @param value
             *     allowed object is
             *     {@link Geography.City.SubCityParent.AlternateCodes }
             *
             */
            public void setAlternateCodes(Geography.City.SubCityParent.AlternateCodes value) {
                this.alternateCodes = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this class.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="AlternateCode" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "alternateCode"
            })
            public static class AlternateCodes {

                @XmlElement(name = "AlternateCode", required = true)
                protected List<Geography.City.SubCityParent.AlternateCodes.AlternateCode> alternateCode;

                /**
                 * Gets the value of the alternateCode property.
                 *
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the alternateCode property.
                 *
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getAlternateCode().add(newItem);
                 * </pre>
                 *
                 *
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Geography.City.SubCityParent.AlternateCodes.AlternateCode }
                 *
                 *
                 */
                public List<Geography.City.SubCityParent.AlternateCodes.AlternateCode> getAlternateCode() {
                    if (alternateCode == null) {
                        alternateCode = new ArrayList<Geography.City.SubCityParent.AlternateCodes.AlternateCode>();
                    }
                    return this.alternateCode;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 *
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="CodeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "codeType",
                        "code"
                })
                public static class AlternateCode {

                    @XmlElement(name = "CodeType", required = true)
                    protected String codeType;
                    @XmlElement(name = "Code", required = true)
                    protected String code;

                    /**
                     * Gets the value of the codeType property.
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCodeType() {
                        return codeType;
                    }

                    /**
                     * Sets the value of the codeType property.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCodeType(String value) {
                        this.codeType = value;
                    }

                    /**
                     * Gets the value of the code property.
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getCode() {
                        return code;
                    }

                    /**
                     * Sets the value of the code property.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setCode(String value) {
                        this.code = value;
                    }

                }
                /***Richa Added*********/

                /**
                 * @param alternateCode the alternateCode to set
                 */
                public void setAlternateCode(List<Geography.City.SubCityParent.AlternateCodes.AlternateCode> alternateCode) {
                    this.alternateCode = alternateCode;
                }
                /***Richa Added*********/

            }

        }

    }


}

