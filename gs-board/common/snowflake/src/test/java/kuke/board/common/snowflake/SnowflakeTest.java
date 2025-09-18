package kuke.board.common.snowflake;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

class SnowflakeTest {
	Snowflake snowflake = new Snowflake();

	/**
	 * 10개의 쓰레드풀을 만들고
	 * 10개의 쓰레드풀이 1000번동안 1000개의 id를 만듦
	 *
	 * result 에 결과가 담기게 되고 오름차순으로 중복없이 잘 생성 됐는지 증명
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@Test
	void nextIdTest() throws ExecutionException, InterruptedException {
		// given
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		List<Future<List<Long>>> futures = new ArrayList<>();
		int repeatCount = 1000;
		int idCount = 1000;

		// when
		for (int i = 0; i < repeatCount; i++) {
			futures.add(executorService.submit(() -> generateIdList(snowflake, idCount)));
		}

		// then
		List<Long> result = new ArrayList<>();
		for (Future<List<Long>> future : futures) {
			List<Long> idList = future.get();
			for (int i = 1; i < idList.size(); i++) {
				assertThat(idList.get(i)).isGreaterThan(idList.get(i - 1));
			}
			result.addAll(idList);
		}
		assertThat(result.stream().distinct().count()).isEqualTo(repeatCount * idCount);

		executorService.shutdown();
	}

	List<Long> generateIdList(Snowflake snowflake, int count) {
		List<Long> idList = new ArrayList<>();
		while (count-- > 0) {
			idList.add(snowflake.nextId());
		}
		return idList;
	}

	/**
	 * 위와 같은 코드 (시간측정을 위한 코드)
	 *
	 * 1,000,000개의 ID를 만드는데 0.2초(246ms)걸린다
	 * snowflake 가 얼마나 빠르게 ID를 만드는지 확인 가능
	 * @throws InterruptedException
	 */
	@Test
	void nextIdPerformanceTest() throws InterruptedException {
		// given
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		int repeatCount = 1000;
		int idCount = 1000;
		CountDownLatch latch = new CountDownLatch(repeatCount);

		// when
		long start = System.nanoTime();
		for (int i = 0; i < repeatCount; i++) {
			executorService.submit(() -> {
				generateIdList(snowflake, idCount);
				latch.countDown();
			});
		}

		latch.await();

		long end = System.nanoTime();
		System.out.println("times = %s ms".formatted((end - start) / 1_000_000));

		executorService.shutdown();
	}
}