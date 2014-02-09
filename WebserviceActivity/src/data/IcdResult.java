package data;

import com.google.gson.annotations.SerializedName;

public class IcdResult
{
	@SerializedName("TextSearchItems")
	public IcdItem[] TextSearchItems;

	@SerializedName("AdditionalRecordPages")
	public boolean AdditionalRecordPages;

	public String[] IcdItems()
	{
		String[] values = new String[TextSearchItems.length];
		for (int index = 0; index < TextSearchItems.length - 1; index++)
		{
			IcdItem item = TextSearchItems[index];
			values[index] = String
					.format("[%s], [%s], [%s], [%s], [%s]", item.Icd9Code, item.Icd10Code, item.Description, item.IsHcc, item.ModifiedVendorCode, item.RootVendorCode);
		}
		return values;
	}
}
