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
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrRegEx;
import org.supercsv.cellprocessor.ift.BoolCellProcessor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.learn.kafkadataproducer.constants.ApplicationConstants;
import com.learn.models.TitleRating;

@RestController
@RequestMapping("/produce")
public class TitleRatingProducer implements BasicProducer<TitleRating> {

	@Autowired
	private KafkaTemplate<String, TitleRating> tRatingProducerKafkaTemplate;

	@Override
	@PostMapping("/title/rating")
	public String sendMessage(@RequestBody TitleRating titleRating) {
		tRatingProducerKafkaTemplate.send(ApplicationConstants.TITLE_RATING_TOPIC_NAME, titleRating.getTconst(),
				titleRating);
		return "Title Rating Sent Successfully";
	}

	@Override
	@PostMapping("/title/rating/all")
	public String sendAllMessage() {

		try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(ApplicationConstants.TITLE_RATING_TSV_FILE_SOURCE_PATH),
				CsvPreference.TAB_PREFERENCE)) {
			// the header elements are used to map the values to the bean
			final String[] headers = beanReader.getHeader(true);
			// final String[] headers = new
			final CellProcessor[] processors = getCellProcessor();

			TitleRating titleRating;
			while ((titleRating = beanReader.read(TitleRating.class, headers, processors)) != null) {
				tRatingProducerKafkaTemplate.send(ApplicationConstants.TITLE_RATING_TOPIC_NAME, titleRating.getTconst(),
						titleRating);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "All Title Ratings Sent Successfully";
	}

	@Override
	public BoolCellProcessor[] getCellProcessor() {
		final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+";
		StrRegEx.registerMessage(emailRegex, "must be a valid email address");

		final BoolCellProcessor[] processors = new BoolCellProcessor[] { new NotNull(), // tconst				
				new NotNull(new ParseDouble()), // avgRating
				new NotNull(new ParseLong()), // numVotes
				
		};
		return processors;
	}

}
