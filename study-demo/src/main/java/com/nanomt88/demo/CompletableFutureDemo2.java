package com.nanomt88.demo;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 模拟转账
 *
 * @author nanomt88@gmail.com
 * @create 2020-11-22 9:03
 **/
public class CompletableFutureDemo2 {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Client client = new Client();
        client.asyncInvoke();
        Thread.sleep(3000);

        CompletableFuture.runAsync(null);
    }
}


class Client {
    @Inject
    private TransferService transferService = new TransferServiceImpl(); // 使用依赖注入获取转账服务的实例
    private final static int A = 1000;
    private final static int B = 1001;

    public void syncInvoke() throws ExecutionException, InterruptedException {
        // 同步调用
        transferService.transfer(A, B, 100).get();
        System.out.println("转账完成！");
    }

    public void asyncInvoke() {
        // 异步调用
        transferService.transfer(A, B, 100)
                .thenRun(() -> System.out.println("转账完成！"));
    }
}

/**
 * 转账服务的实现
 */
class AccountServiceImpl implements AccountService {

    @Override
    public CompletableFuture<Void> add(int account, int amount) {

        return CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    if (account == 1000) {
                        Thread.sleep(2000);
                    }else if(account == 1001){
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(account + " 余额变动 " + amount);
            }
        });
    }
}

/**
 * 转账服务的实现
 */
class TransferServiceImpl implements TransferService {
    @Inject
    private AccountService accountService = new AccountServiceImpl(); // 使用依赖注入获取账户服务的实例

    @Override
    public CompletableFuture<Void> transfer(int fromAccount, int toAccount, int amount) {
        // 异步调用add方法从fromAccount扣减相应金额
        return accountService.add(fromAccount, -1 * amount)
                // 然后调用add方法给toAccount增加相应金额
                .thenCompose(v -> accountService.add(toAccount, amount));
    }
}

/**
 * 账户服务
 */
interface AccountService {
    /**
     * 变更账户金额
     *
     * @param account 账户ID
     * @param amount  增加的金额，负值为减少
     */
    CompletableFuture<Void> add(int account, int amount);
}


/**
 * 转账服务
 */
interface TransferService {
    /**
     * 异步转账服务
     *
     * @param fromAccount 转出账户
     * @param toAccount   转入账户
     * @param amount      转账金额，单位分
     */
    CompletableFuture<Void> transfer(int fromAccount, int toAccount, int amount);
}