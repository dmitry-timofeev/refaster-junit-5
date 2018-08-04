package dt.refaster.junit;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.errorprone.refaster.ImportPolicy;
import com.google.errorprone.refaster.annotation.AfterTemplate;
import com.google.errorprone.refaster.annotation.BeforeTemplate;
import com.google.errorprone.refaster.annotation.Placeholder;
import com.google.errorprone.refaster.annotation.UseImportPolicy;
import org.junit.rules.ExpectedException;

/**
 * Do not run it first if you match on exceptions.
 */
abstract class ExpectedExceptionTrivialRule {

	@Placeholder abstract void callUnderTest();

	@BeforeTemplate
	void before(ExpectedException rule,
			Class<? extends Throwable> exceptionType) {
		rule.expect(exceptionType);
		callUnderTest();
	}

	@AfterTemplate
	@UseImportPolicy(ImportPolicy.STATIC_IMPORT_ALWAYS)
	void after(@SuppressWarnings("unused") ExpectedException rule,
			Class<? extends Throwable> exceptionType) {
		assertThrows(exceptionType,
				() -> callUnderTest());
	}
}
