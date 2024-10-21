package assignment10.garbageCollection;

class ResourceWithFinalizer implements AutoCloseable {
	public ResourceWithFinalizer() {
		System.out.println("Resource allocated: " + this);
	}

	@Override
	protected void finalize() {
		System.out.println("Resource cleaned up (finalize): " + this);
	}

	@Override
	public void close() {
		System.out.println("Resource cleaned up (close): " + this);

	}
}

public class Program3 {

	public static void useResource() {
		try (ResourceWithFinalizer resource = new ResourceWithFinalizer()) {
			System.out.println("Using resource: " + resource);
		} catch (Exception e) {
			System.out.println("Exception occurred: " + e.getMessage());
		}
	}

	public static void main(String[] args) {

		System.out.println("Using try-with-resources:");
		useResource();

		System.gc();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		System.out.println("Main method finished execution.");
	}
}
