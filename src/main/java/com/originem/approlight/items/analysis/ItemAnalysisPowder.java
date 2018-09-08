package com.originem.approlight.items.analysis;

import com.originem.approlight.items.ItemBase;

public class ItemAnalysisPowder extends ItemBase {
    private int repairAmount;

    public ItemAnalysisPowder(String name, int repairAmount) {
        super(name);
        this.repairAmount = repairAmount;
    }

    public int getRepairAmount() {
        return repairAmount;
    }
}
