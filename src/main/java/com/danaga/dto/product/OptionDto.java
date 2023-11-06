package com.danaga.dto.product;

import java.util.ArrayList;
import java.util.List;

import com.danaga.entity.OptionSet;
import com.danaga.entity.Options;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {
	private String name;
	private String value;
	
	public OptionDto(Options t) {
		this.name=t.getName();
		this.value=t.getValue();
	}
	
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class OptionNameValueDto {
		private List<String> optionNames;
		private List<String> optionValues;
	}
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class OptionNameValueMapDto {
		private String optionName;
		@Builder.Default
		private List<String> optionValue=new ArrayList<String>();
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class OptionSaveDto extends OptionDto{
		private Integer extraPrice;
		private Long optionSetId;
		
		public Options toEntity() {
			return Options.builder()
					.name(super.name)
					.value(super.value)
					.optionSet(OptionSet.builder().id(optionSetId).build())
					.extraPrice(extraPrice)
					.build();
		}
	}
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class OptionUpdateDto extends OptionSaveDto{
		private Long id;
		
		public Options toEntity() {
			return Options.builder()
					.id(id)
					.name(name)
					.value(value)
					.extraPrice(super.extraPrice)
					.optionSet(OptionSet.builder().id(super.optionSetId).build())
					.build();
		}
		public OptionUpdateDto(Options entity){
			this.id=entity.getId();
		}
		public OptionSaveDto toSaveDto() {
			return (OptionSaveDto) OptionSaveDto.builder()
					.name(name)
					.value(value)
					.build();
		}
	}
}
