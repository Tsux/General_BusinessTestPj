package doubler.impl;

import doubler.Doubler;
import org.junit.Test;

public class DoublerImplTest {
    
    @Test
    public void testIt() {
    	System.out.println("damm! It Works buddy");
    	Doubler doubler = new DoublerImpl();
        assert doubler.doubleIt(4) == 8;
    }
}
