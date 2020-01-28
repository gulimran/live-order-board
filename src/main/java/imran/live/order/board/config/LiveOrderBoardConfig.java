package imran.live.order.board.config;

import imran.live.order.board.api.LiveOrderBoardService;
import imran.live.order.board.dao.DataSource;
import imran.live.order.board.dao.LiveOrderBoardDao;
import imran.live.order.board.dao.LiveOrderBoardDaoInMemory;
import imran.live.order.board.service.LiveOrderBoardServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiveOrderBoardConfig {

    @Bean
    public LiveOrderBoardService liveOrderBoardService() {
        return new LiveOrderBoardServiceImpl(liveOrderBoardDao());
    }

    @Bean
    public LiveOrderBoardDao liveOrderBoardDao() {
        return new LiveOrderBoardDaoInMemory(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        return new DataSource();
    }
}
