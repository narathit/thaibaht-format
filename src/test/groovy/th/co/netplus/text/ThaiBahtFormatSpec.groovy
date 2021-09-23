package th.co.netplus.text

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest(classes=ThaiBahtFormatApplication)
@Slf4j
class ThaiBahtFormatSpec extends Specification {
	
	@Subject
	ThaiBahtFormat format
	
	def setup() {
	}

	def 'test loadresouces should return set properties'() {
				
		expect:
		assert ThaiBahtFormat.SCALES.size() == 7
		assert ThaiBahtFormat.DIGITS.size() == 10
		assert ThaiBahtFormat.SYMBOLS.size() == 9
	}
	
	def 'test method format(BigDecimal) should return set valid String'() {	
		given:
			format = new ThaiBahtFormat()		
		expect:
		format.format(BigDecimal.valueOf(10l)) == 'สิบบาทถ้วน'
		format.format(-1) == 'ลบหนึ่งบาทถ้วน'
		format.format(1) == 'หนึ่งบาทถ้วน'
		format.format(-1257.5463) == 'ลบหนึ่งพันสองร้อยห้าสิบเจ็ดบาทห้าสิบห้าสตางค์'
		format.format(423720.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยยี่สิบบาทถ้วน'
		format.format(423721.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยยี่สิบเอ็ดบาทถ้วน'
		format.format(423710.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยสิบบาทถ้วน'
		format.format(423711.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยสิบเอ็ดบาทถ้วน'
		format.format(423701.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยเอ็ดบาทถ้วน'
		format.format(423791.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบเอ็ดบาทถ้วน'
		format.format(423790.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบบาทถ้วน'
		format.format(9423790.00) == 'เก้าล้านสี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบบาทถ้วน'
		format.format(99423790.00) == 'เก้าสิบเก้าล้านสี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบบาทถ้วน'
		format.format(999423790.00) == 'เก้าร้อยเก้าสิบเก้าล้านสี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบบาทถ้วน'
	}
		
	def 'test static method convert(BigDecimal) should return set valid String'() {	
		expect:
		ThaiBahtFormat.convert(BigDecimal.valueOf(10l)) == 'สิบบาทถ้วน'
		ThaiBahtFormat.convert(-1) == 'ลบหนึ่งบาทถ้วน'
		ThaiBahtFormat.convert(1) == 'หนึ่งบาทถ้วน'
		ThaiBahtFormat.convert(-1257.5463) == 'ลบหนึ่งพันสองร้อยห้าสิบเจ็ดบาทห้าสิบห้าสตางค์'
		ThaiBahtFormat.convert(423720.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยยี่สิบบาทถ้วน'
		ThaiBahtFormat.convert(423721.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยยี่สิบเอ็ดบาทถ้วน'
		ThaiBahtFormat.convert(423710.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยสิบบาทถ้วน'
		ThaiBahtFormat.convert(423711.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยสิบเอ็ดบาทถ้วน'
		ThaiBahtFormat.convert(423701.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยเอ็ดบาทถ้วน'
		ThaiBahtFormat.convert(423791.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบเอ็ดบาทถ้วน'
		ThaiBahtFormat.convert(423790.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบบาทถ้วน'
		ThaiBahtFormat.convert(9423790.00) == 'เก้าล้านสี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบบาทถ้วน'
		ThaiBahtFormat.convert(99423790.00) == 'เก้าสิบเก้าล้านสี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบบาทถ้วน'
		ThaiBahtFormat.convert(999423790.00) == 'เก้าร้อยเก้าสิบเก้าล้านสี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบบาทถ้วน'
	}

}
