package th.co.netplus.text

import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode
import java.nio.charset.StandardCharsets

import org.apache.commons.configuration2.Configuration
import org.apache.commons.configuration2.PropertiesConfiguration
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder
import org.apache.commons.configuration2.builder.fluent.Configurations
import org.apache.commons.configuration2.builder.fluent.Parameters
import org.apache.commons.configuration2.ex.ConfigurationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import groovy.util.logging.Slf4j

@Component
@Slf4j
class ThaiBahtFormat{

	static String[] SCALES
	
	static String[] DIGITS
	
	static String[] SYMBOLS
	
	static {
		try {
			ResourceLoader resourceLoader = new DefaultResourceLoader()
			Resource resource = resourceLoader.getResource("classpath:thaiBaht.properties")
			Properties props = new Properties()
			props.load(new InputStreamReader(resource.inputStream, StandardCharsets.UTF_8))
			SCALES = props.get('scales').split(",")*.trim()
			DIGITS = props.get('digits').split(",")*.trim()
			SYMBOLS = props.get('symbols').split(",")*.trim()
		}catch(Exception e) {
			log.error(e.message, e)
		}
	}
	
	private String getNumberText(BigInteger number) {
		log.debug "number: ${number}"
		StringBuffer buffer = new StringBuffer()
		char[] digits = number.toString().toCharArray()
		for (int index = digits.length; index > 0; --index) {
			int digit = Integer.parseInt(String.valueOf(digits[digits.length - index]))
			String digit_text = DIGITS[digit]
			int scale_idx = ((1 < index) ? ((index - 1) % 6) : 6)
			if ((1 == scale_idx) && (2 == digit)) {
				digit_text = SYMBOLS[4].toString()
			}
			if (1 == digit) {
				switch (scale_idx) {
					case 0:
					case 6:
						buffer.append((index < digits.length) ? SYMBOLS[5].toString() : digit_text)
						break
					case 1:
						break
					default:
						buffer.append(digit_text)
						break
				}
			} else if (0 == digit) {
				if (0 == scale_idx) {
					buffer.append(SCALES[scale_idx])
				}
				continue
			} else {
				buffer.append(digit_text)
			}
			buffer.append(SCALES[scale_idx])
		}
		return buffer.toString()
	}
				
	String format(BigDecimal number) {
		StringBuilder builder = new StringBuilder()
		BigDecimal absolute = number.abs()
		int precision = absolute.precision()
		int scale = absolute.scale()
		int rounded_precision = ((precision - scale) + 2)
		MathContext mc = new MathContext(rounded_precision, RoundingMode.HALF_UP)
		BigDecimal rounded = absolute.round(mc)
		BigDecimal[] compound = rounded.divideAndRemainder(BigDecimal.ONE)
		boolean negative_amount = (-1 == number.compareTo(BigDecimal.ZERO))
 
		compound[0] = compound[0].setScale(0)
		compound[1] = compound[1].movePointRight(2)
 
		if (negative_amount) {
			builder.append(SYMBOLS[0].toString())
		}
 
		builder.append(getNumberText(compound[0].toBigIntegerExact()))
		builder.append(SYMBOLS[1].toString())
 
		if (0 == compound[1].compareTo(BigDecimal.ZERO)) {
			builder.append(SYMBOLS[2].toString())
		} else {
			builder.append(getNumberText(compound[1].toBigIntegerExact()))
			builder.append(SYMBOLS[3].toString())
			builder.append(SYMBOLS[2].toString())
		}
		return builder.toString()
	}
 
	String format(BigInteger number) {
		return format(new BigDecimal(number))
	}
	String format(double number) {
		return format(new BigDecimal(number))
	}
	String format(float number) {
		return format(new BigDecimal(number))
	}
	String format(int number) {
		return format(new BigDecimal(number))
	}
	String format(long number) {
		return format(new BigDecimal(number))
	}

	static String convert(BigDecimal number)	{
		return new ThaiBahtFormat().format(number)
	}
	
	static String convert(BigInteger number)	{
		return ThaiBahtFormat.convert(new BigDecimal(number))
	}

	static String convert(double number)	{
		return ThaiBahtFormat.convert(new BigDecimal(number))
	}
	static String convert(float number)	{
		return ThaiBahtFormat.convert(new BigDecimal(number))
	}
	static String convert(int number)	{
		return ThaiBahtFormat.convert(new BigDecimal(number))
	}
	static String convert(long number)	{
		return ThaiBahtFormat.convert(new BigDecimal(number))
	}
}
