import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
	private Resume[] storage = new Resume[10000];
	private int size = 0;

	void clear() {
		Arrays.fill(storage, null);
		size = 0;
	}

	void save(Resume input) {
		for (Resume resume : storage) {
			if ((resume != null) && (input.toString().equals(resume.toString()))) {
				System.out.println("Неуникальный(повторяющийся) uuid");
				return;
			}
		}
		storage[size] = input;
		size++;
	}

	Resume get(String uuid) {
		for (Resume resume : storage) {
			if ((resume != null) && (resume.toString().equals(uuid))) {
				return resume;
			}
		}
		return null;
	}

	void delete(String uuid) {

		for (int i = 0; i < size; i++) {
			if (storage[i].uuid.equals(uuid)) {
				size--;
				if (size - i >= 0) {
					System.arraycopy(storage, i + 1, storage, i, size - i);
				}
			}

		}
	}

	/**
	 * @return array, contains only Resumes in storage (without null)
	 */
	Resume[] getAll() {

		Resume[] notnull = new Resume[size];
		System.arraycopy(storage, 0, notnull, 0, size);

		return notnull;
	}

	int size() {
		return size;
	}
}
