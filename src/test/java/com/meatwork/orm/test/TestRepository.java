package com.meatwork.orm.test;


import com.meatwork.core.api.service.ApplicationStartup;
import com.meatwork.core.api.service.MeatworkApplication;
import com.meatwork.orm.api.EntityRef;
import com.meatwork.orm.test.model.AnEntity;
import com.meatwork.orm.test.model.AnEntity2;
import com.meatwork.orm.test.model.AnEntity2Repository;
import com.meatwork.orm.test.model.AnEntityRepository;
import com.meatwork.test.api.MeatworkExtension;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

/*
 * Copyright (c) 2025 Taliro.
 * All rights reserved.
 */
@ExtendWith(MeatworkExtension.class)
public class TestRepository {

	@Inject
	private AnEntityRepository anEntityRepository;
	@Inject
	private AnEntity2Repository anEntity2Repository;
	@Inject
	private Set<ApplicationStartup> applicationStartups;

	@Test
	public void testRepositorySave() throws Exception {
		applicationStartups.iterator().next().run(ApplicationTest.class, null);

		var anEntity2 = new AnEntity2();
		anEntity2.setId(UUID.randomUUID().toString());
		anEntity2.setName("AnEntity2");
		anEntity2Repository.save(anEntity2);

		var anEntity = new AnEntity();
		var id = UUID
				.randomUUID()
				.toString();
		anEntity.setId(id);
		anEntity.setName("An Name");
		anEntity.setToto("An Toto");
		anEntity.setAge(10);
		anEntity.setBigdecimal(new BigDecimal(10));
		LocalDate nowDate = LocalDate.now();
		anEntity.setDate(nowDate);
		LocalTime nowTime = LocalTime.now();
		anEntity.setTime(nowTime);
		LocalDateTime nowDateTime = LocalDateTime.now();
		anEntity.setDatetime(nowDateTime);
		anEntity.setNumberId(99999999999L);
		anEntity.setRef(new EntityRef<>(anEntity2, anEntity2.getId()));

		anEntityRepository.save(anEntity);

		var anEntityFromDb = anEntityRepository.findById(id);
		Assertions.assertNotNull(anEntityFromDb);
		Assertions.assertEquals("An Name", anEntityFromDb.getName());
		Assertions.assertEquals("An Toto", anEntityFromDb.getToto());
		Assertions.assertEquals(10, anEntityFromDb.getAge());
		Assertions.assertEquals(new BigDecimal(10).setScale(2, RoundingMode.DOWN), anEntityFromDb.getBigdecimal());
		Assertions.assertEquals(nowDate, anEntityFromDb.getDate());
		Assertions.assertEquals(nowTime.getHour(), anEntityFromDb.getTime().getHour());
		Assertions.assertEquals(nowTime.getMinute(), anEntityFromDb.getTime().getMinute());
		Assertions.assertEquals(nowTime.getSecond(), anEntityFromDb.getTime().getSecond());
		Assertions.assertEquals(nowDateTime.getHour(), anEntityFromDb.getDatetime().getHour());
		Assertions.assertEquals(nowDateTime.getMinute(), anEntityFromDb.getDatetime().getMinute());
		Assertions.assertEquals(nowDateTime.getSecond(), anEntityFromDb.getDatetime().getSecond());
		Assertions.assertEquals(99999999999L, anEntityFromDb.getNumberId());
		Assertions.assertEquals(anEntity2.getId(), anEntityFromDb.getRef().id());

	}

	@MeatworkApplication
	public static class ApplicationTest {}
}
