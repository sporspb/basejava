/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                storage[i] = null;
            }
            size = 0;
        }
    }

    void save(Resume input) {
        for (int i = 0; i < size; i++) {
            Resume resume = storage[i];
            if (input.toString().equals(resume.toString())) {
                System.out.println("Неуникальный(повторяющийся) uuid");
                return;
            }
        }
        storage[size] = input;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            Resume resume = storage[i];
            if (resume.toString().equals(uuid)) {
                return resume;
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            Resume resume = storage[i];
            if (resume.toString().equals(uuid)) {
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
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    int size() {
        return size;
    }
}
