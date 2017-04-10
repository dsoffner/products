
package generated_1491451893007;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="arrivalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="fromPostcode" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="productIds" maxOccurs="unbounded" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="EPARCEL-EXP"/>
 *               &lt;enumeration value="EPARCEL-REG"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="toPostcode" type="{http://www.w3.org/2001/XMLSchema}short"/>
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
    "arrivalDate",
    "fromPostcode",
    "productIds",
    "toPostcode"
})
@XmlRootElement(name = "getProductInfo")
public class GetProductInfo {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar arrivalDate;
    protected short fromPostcode;
    protected List<String> productIds;
    protected short toPostcode;

    /**
     * Gets the value of the arrivalDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Sets the value of the arrivalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setArrivalDate(XMLGregorianCalendar value) {
        this.arrivalDate = value;
    }

    /**
     * Gets the value of the fromPostcode property.
     * 
     */
    public short getFromPostcode() {
        return fromPostcode;
    }

    /**
     * Sets the value of the fromPostcode property.
     * 
     */
    public void setFromPostcode(short value) {
        this.fromPostcode = value;
    }

    /**
     * Gets the value of the productIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getProductIds() {
        if (productIds == null) {
            productIds = new ArrayList<String>();
        }
        return this.productIds;
    }

    /**
     * Gets the value of the toPostcode property.
     * 
     */
    public short getToPostcode() {
        return toPostcode;
    }

    /**
     * Sets the value of the toPostcode property.
     * 
     */
    public void setToPostcode(short value) {
        this.toPostcode = value;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

}
