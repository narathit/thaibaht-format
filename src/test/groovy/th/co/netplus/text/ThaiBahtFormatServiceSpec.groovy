package th.co.netplus.text

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import groovy.util.logging.Slf4j

@SpringBootTest(classes=ThaiBahtFormatApplication)
@Slf4j
class ThaiBahtFormatServiceSpec {
	
	@Autowired
	ThaiBahtFormat service
	
	
	def setup() {
	}

	def 'test as bean should be inited and available.'() {
				
		expect:
		assert service.SCALES.size() == 7
		assert service.DIGITS.size() == 10
		assert service.SYMBOLS.size() == 9
		service.format(BigDecimal.valueOf(10l)) == 'สิบบาทถ้วน'
		service.format(-1) == 'ลบหนึ่งบาทถ้วน'
		service.format(1) == 'หนึ่งบาทถ้วน'
		service.format(-1257.5463) == 'ลบหนึ่งพันสองร้อยห้าสิบเจ็ดบาทห้าสิบห้าสตางค์ถ้วน'
		service.format(-1257.55) == 'ลบหนึ่งพันสองร้อยห้าสิบเจ็ดบาทห้าสิบห้าสตางค์ถ้วน'
		service.format(1257.5463) == 'หนึ่งพันสองร้อยห้าสิบเจ็ดบาทห้าสิบห้าสตางค์ถ้วน'
		service.format(1257.55) == 'หนึ่งพันสองร้อยห้าสิบเจ็ดบาทห้าสิบห้าสตางค์ถ้วน'
		service.format(423720.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยยี่สิบบาทถ้วน'
		service.format(423721.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยยี่สิบเอ็ดบาทถ้วน'
		service.format(423710.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยสิบบาทถ้วน'
		service.format(423711.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยสิบเอ็ดบาทถ้วน'
		service.format(423701.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยเอ็ดบาทถ้วน'
		service.format(423791.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบเอ็ดบาทถ้วน'
		service.format(423790.00) == 'สี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบบาทถ้วน'
		service.format(9423790.00) == 'เก้าล้านสี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบบาทถ้วน'
		service.format(99423790.00) == 'เก้าสิบเก้าล้านสี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบบาทถ้วน'
		service.format(999423790.00) == 'เก้าร้อยเก้าสิบเก้าล้านสี่แสนสองหมื่นสามพันเจ็ดร้อยเก้าสิบบาทถ้วน'
	}

	
}
