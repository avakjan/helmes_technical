package com.example.demo.sector;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SectorDataLoader implements ApplicationRunner {

	private final SectorRepository sectorRepository;

	public SectorDataLoader(SectorRepository sectorRepository) {
		this.sectorRepository = sectorRepository;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (sectorRepository.count() > 0) {
			return;
		}
		List<Sector> sectors = new ArrayList<>();

		int order = 1;
		sectors.add(new Sector(1L, "Manufacturing", null, order++));
		sectors.add(new Sector(19L, "Construction materials", 1L, order++));
		sectors.add(new Sector(18L, "Electronics and Optics", 1L, order++));
		sectors.add(new Sector(6L, "Food and Beverage", 1L, order++));
		sectors.add(new Sector(342L, "Bakery & confectionery products", 6L, order++));
		sectors.add(new Sector(43L, "Beverages", 6L, order++));
		sectors.add(new Sector(42L, "Fish & fish products", 6L, order++));
		sectors.add(new Sector(40L, "Meat & meat products", 6L, order++));
		sectors.add(new Sector(39L, "Milk & dairy products", 6L, order++));
		sectors.add(new Sector(437L, "Other", 6L, order++));
		sectors.add(new Sector(378L, "Sweets & snack food", 6L, order++));
		sectors.add(new Sector(13L, "Furniture", 1L, order++));
		sectors.add(new Sector(389L, "Bathroom/sauna", 13L, order++));
		sectors.add(new Sector(385L, "Bedroom", 13L, order++));
		sectors.add(new Sector(390L, "Children’s room", 13L, order++));
		sectors.add(new Sector(98L, "Kitchen", 13L, order++));
		sectors.add(new Sector(101L, "Living room", 13L, order++));
		sectors.add(new Sector(392L, "Office", 13L, order++));
		sectors.add(new Sector(394L, "Other (Furniture)", 13L, order++));
		sectors.add(new Sector(341L, "Outdoor", 13L, order++));
		sectors.add(new Sector(99L, "Project furniture", 13L, order++));
		sectors.add(new Sector(12L, "Machinery", 1L, order++));
		sectors.add(new Sector(94L, "Machinery components", 12L, order++));
		sectors.add(new Sector(91L, "Machinery equipment/tools", 12L, order++));
		sectors.add(new Sector(224L, "Manufacture of machinery", 12L, order++));
		sectors.add(new Sector(97L, "Maritime", 12L, order++));
		sectors.add(new Sector(271L, "Aluminium and steel workboats", 97L, order++));
		sectors.add(new Sector(269L, "Boat/Yacht building", 97L, order++));
		sectors.add(new Sector(230L, "Ship repair and conversion", 97L, order++));
		sectors.add(new Sector(93L, "Metal structures", 12L, order++));
		sectors.add(new Sector(508L, "Other", 12L, order++));
		sectors.add(new Sector(227L, "Repair and maintenance service", 12L, order++));
		sectors.add(new Sector(11L, "Metalworking", 1L, order++));
		sectors.add(new Sector(67L, "Construction of metal structures", 11L, order++));
		sectors.add(new Sector(263L, "Houses and buildings", 11L, order++));
		sectors.add(new Sector(267L, "Metal products", 11L, order++));
		sectors.add(new Sector(542L, "Metal works", 11L, order++));
		sectors.add(new Sector(75L, "CNC-machining", 542L, order++));
		sectors.add(new Sector(62L, "Forgings, Fasteners", 542L, order++));
		sectors.add(new Sector(69L, "Gas, Plasma, Laser cutting", 542L, order++));
		sectors.add(new Sector(66L, "MIG, TIG, Aluminum welding", 542L, order++));
		sectors.add(new Sector(9L, "Plastic and Rubber", 1L, order++));
		sectors.add(new Sector(54L, "Packaging", 9L, order++));
		sectors.add(new Sector(556L, "Plastic goods", 9L, order++));
		sectors.add(new Sector(559L, "Plastic processing technology", 9L, order++));
		sectors.add(new Sector(55L, "Blowing", 559L, order++));
		sectors.add(new Sector(57L, "Moulding", 559L, order++));
		sectors.add(new Sector(53L, "Plastics welding and processing", 9L, order++));
		sectors.add(new Sector(560L, "Plastic profiles", 9L, order++));
		sectors.add(new Sector(5L, "Printing", 1L, order++));
		sectors.add(new Sector(148L, "Advertising", 5L, order++));
		sectors.add(new Sector(150L, "Book/Periodicals printing", 5L, order++));
		sectors.add(new Sector(145L, "Labelling and packaging printing", 5L, order++));
		sectors.add(new Sector(7L, "Textile and Clothing", 1L, order++));
		sectors.add(new Sector(44L, "Clothing", 7L, order++));
		sectors.add(new Sector(45L, "Textile", 7L, order++));
		sectors.add(new Sector(8L, "Wood", 1L, order++));
		sectors.add(new Sector(337L, "Other (Wood)", 8L, order++));
		sectors.add(new Sector(51L, "Wooden building materials", 8L, order++));
		sectors.add(new Sector(47L, "Wooden houses", 8L, order++));
		sectors.add(new Sector(3L, "Other", 1L, order++));
		sectors.add(new Sector(37L, "Creative industries", 3L, order++));
		sectors.add(new Sector(29L, "Energy technology", 3L, order++));
		sectors.add(new Sector(33L, "Environment", 3L, order++));
		sectors.add(new Sector(2L, "Service", 1L, order++));
		sectors.add(new Sector(25L, "Business services", 2L, order++));
		sectors.add(new Sector(35L, "Engineering", 2L, order++));
		sectors.add(new Sector(28L, "Information Technology and Telecommunications", 2L, order++));
		sectors.add(new Sector(581L, "Data processing, Web portals, E-marketing", 28L, order++));
		sectors.add(new Sector(576L, "Programming, Consultancy", 28L, order++));
		sectors.add(new Sector(121L, "Software, Hardware", 28L, order++));
		sectors.add(new Sector(122L, "Telecommunications", 28L, order++));
		sectors.add(new Sector(22L, "Tourism", 2L, order++));
		sectors.add(new Sector(141L, "Translation services", 2L, order++));
		sectors.add(new Sector(21L, "Transport and Logistics", 2L, order++));
		sectors.add(new Sector(111L, "Air", 21L, order++));
		sectors.add(new Sector(114L, "Rail", 21L, order++));
		sectors.add(new Sector(112L, "Road", 21L, order++));
		sectors.add(new Sector(113L, "Water", 21L, order++));

		sectorRepository.saveAll(sectors);
	}
}
