package classes;

public class Servicetype {
	private int stype_id;
	private String name;
	private Long price;
	private String unit;
	private String description;

	public Servicetype(int stype_id, String name, String unit, Long price, String description) {
		this.stype_id = stype_id;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.description = description;
	}

	public Servicetype(int stype_id, String name, String unit) {
		this.stype_id = stype_id;
		this.name = name;
		this.unit = unit;
	}

	@Override
	public String toString() {
		return name;
	}

	public int getStype_id() {
		return stype_id;
	}

	public void setStype_id(int stype_id) {
		this.stype_id = stype_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
