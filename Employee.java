package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private final String employeeId;
	private final String firstName;
	private final String lastName;
	private final String idNumber;
	private final String address;

	private final int yearJoined;
	private final int monthJoined;
	private final int dayJoined;
	private final boolean isForeigner;
	private final boolean gender; // true = Laki-laki, false = Perempuan by farassss

	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;

	private String spouseName;
	private String spouseIdNumber;

	private List<String> childNames;
	private List<String> childIdNumbers;

	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address,
			int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, boolean gender) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.yearJoined = yearJoined;
		this.monthJoined = monthJoined;
		this.dayJoined = dayJoined;
		this.isForeigner = isForeigner;
		this.gender = gender;

		childNames = new LinkedList<>();
		childIdNumbers = new LinkedList<>();
	}

	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade
	 * kepegawaiannya.
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 * 
	 * @param grade grade kepegawaiannya (1: grade 1, 2: grade 2, 3: grade 3)
	 */
	public void setMonthlySalary(int grade) {
		switch (grade) {
			case 1:
				monthlySalary = isForeigner ? 4500000 : 3000000;
				break;
			case 2:
				monthlySalary = isForeigner ? 7500000 : 5000000;
				break;
			case 3:
				monthlySalary = isForeigner ? 10500000 : 7000000;
				break;
			default:
				monthlySalary = 0;
				break;
		}
	}

	/**
	 * Fungsi untuk mengatur potongan pajak tahunan
	 * 
	 * @param deductible jumlah potongan pajak tahunan
	 */
	public void setAnnualDeductible(int deductible) {
		this.annualDeductible = deductible;
	}

	/**
	 * Fungsi untuk mengatur penghasilan tambahan
	 * 
	 * @param income jumlah penghasilan tambahan
	 */
	public void setAdditionalIncome(int income) {
		this.otherMonthlyIncome = income;
	}

	/**
	 * Fungsi untuk mengatur data pasangan
	 * 
	 * @param spouseName     nama pasangan
	 * @param spouseIdNumber nomor identitas pasangan
	 */
	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouseName = spouseName;
		this.spouseIdNumber = spouseIdNumber;
	}

	/**
	 * Fungsi untuk menambahkan data anak
	 * 
	 * @param childName     nama anak
	 * @param childIdNumber nomor identitas anak
	 */
	public void addChild(String childName, String childIdNumber) {
		if (childName != null && childIdNumber != null && !childName.isEmpty() && !childIdNumber.isEmpty()) {
			childNames.add(childName);
			childIdNumbers.add(childIdNumber);
		}
	} // diperbaiki untuk memeriksa apakah childName dan childIdNumber tidak kosong
		// atau null sebelum ditambahkan ke dalam list by faras

	/**
	 * Fungsi untuk menghitung pajak tahunan
	 * 
	 * @return pajak tahunan
	 */
	public int getAnnualIncomeTax() {

		// Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah
		// by farass
		// bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan. by farass
		LocalDate currentDate = LocalDate.now();
		LocalDate joinedDate = LocalDate.of(yearJoined, Month.of(monthJoined), dayJoined);
		int monthWorkingInYear = (currentDate.getYear() == yearJoined) ? currentDate.getMonthValue() - monthJoined + 1
				: 12;

		return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthWorkingInYear, annualDeductible,
				spouseIdNumber.equals(""), childIdNumbers.size());
	}
}
