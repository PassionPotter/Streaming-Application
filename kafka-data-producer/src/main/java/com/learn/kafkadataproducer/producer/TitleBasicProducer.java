package com.learn.kafkadataproducer.producer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrRegEx;
import org.supercsv.cellprocessor.ift.BoolCellProcessor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.learn.kafkadataproducer.constants.ApplicationConstants;
import com.learn.models.TitleBasic;

@RestController
@RequestMapping("/produce")
public class TitleBasicProducer implements BasicProducer<TitleBasic> {

	@Autowired
	private KafkaTemplate<String, TitleBasic> tBasicProducerKafkaTemplate;

	@Override
	@PostMapping("/title/basic")
	public String sendMessage(@RequestBody TitleBasic titleBasic) {
		tBasicProducerKafkaTemplate.send(ApplicationConstants.TITLE_BASIC_TOPIC_NAME, titleBasic.getTconst(),
				titleBasic);
		return "Title Basic Sent Successfully";
	}

	@Override
	@PostMapping("/title/basic/all")
	public String sendAllMessage() {

		try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(ApplicationConstants.TITLE_BASIC_TSV_FILE_SOURCE_PATH),
				CsvPreference.TAB_PREFERENCE)) {
			// the header elements are used to map the values to the bean
			final String[] headers = beanReader.getHeader(true);
			// final String[] headers = new
			final CellProcessor[] processors = getTitleBasicProcessor();

			TitleBasic titleBasic;
			while ((titleBasic = beanReader.read(TitleBasic.class, headers, processors)) != null) {
				tBasicProducerKafkaTemplate.send(ApplicationConstants.TITLE_BASIC_TOPIC_NAME, titleBasic.getTconst(),
						titleBasic);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "All Title Basics Sent Successfully";
	}

	/**
	 * Sets up the processors used for the examples.
	 */
	private BoolCellProcessor[] getTitleBasicProcessor() {
		final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+";
		StrRegEx.registerMessage(emailRegex, "must be a valid email address");

		final BoolCellProcessor[] processors = new BoolCellProcessor[] { new NotNull(), // tconst
				new NotNull(), // titleType
				new NotNull(), // primaryTitle
				new NotNull(), // originalTitle
				new NotNull(new ParseBool()), // isAdult
				new NotNull(new ParseInt()), // startYear
				new Optional(), // endYear
				new NotNull(new ParseInt()), // runtimeMinutes
				new NotNull(), // genres
		};
		return processors;
	}

}
