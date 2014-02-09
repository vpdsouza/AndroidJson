package data;

import com.google.gson.annotations.SerializedName;

public class IcdItem {
	@SerializedName("Description")
	public String Description = "";

	@SerializedName("Icd9Code")
	public String Icd9Code = "";
	
	@SerializedName("Icd10Code")
	public String Icd10Code = "";

	@SerializedName("IsHcc")
	public boolean IsHcc;

	@SerializedName("ModifiedVendorCode")
	public String ModifiedVendorCode = "";

	@SerializedName("RootVendorCode")
	public String RootVendorCode = "";

	@SerializedName("SelectedVendorModifierCodes")
	public String SelectedVendorModifierCodes = "";
}
