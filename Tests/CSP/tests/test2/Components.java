/*
 * To change this template. choose Tools | Templates
 * and open the template in the editor.
 */
package CSP.tests.test2;

import CSP.CSP.CSP;
import CSP.CSP.VariableOrderingHeuristics;
/**
 *
 * @author Lay
 */
public class Components {

    static int[] heuristics = {};

    public static Integer hiperheuristic(CSP csp,
            Double mfd_Density, Double mfd_Tightness,
            Double bbz_Density, Double bbz_Tightness,
            Double ens_Density, Double ens_Tightness,
            Double mxc_Density, Double mxc_Tightness,
            Double fbz_Density, Double fbz_Tightness,
            Double kappa_Density, Double kappa_Tightness,
            Double rho_Density, Double rho_Tightness) {

        double cspDensity = csp.getFeature(CSP.CONSTRAINT_DENSITY);
        double cspTightness = csp.getFeature(CSP.CONSTRAINT_TIGHTNESS);

        double mfdDistance = Math.pow(Math.pow((cspDensity - mfd_Density), 2)
                + Math.pow((cspTightness - mfd_Tightness), 2), .5);

        double bbzDistance = Math.pow(Math.pow((cspDensity - bbz_Density), 2)
                + Math.pow((cspTightness - bbz_Tightness), 2), .5);

        double ensDistance = Math.pow(Math.pow((cspDensity - ens_Density), 2)
                + Math.pow((cspTightness - ens_Tightness), 2), .5);

        double mxcDistance = Math.pow(Math.pow((cspDensity - mxc_Density), 2)
                + Math.pow((cspTightness - mxc_Tightness), 2), .5);

        double fbzDistance = Math.pow(Math.pow((cspDensity - fbz_Density), 2)
                + Math.pow((cspTightness - fbz_Tightness), 2), .5);

        double kappaDistance = Math.pow(Math.pow((cspDensity - kappa_Density), 2)
                + Math.pow((cspTightness - kappa_Tightness), 2), .5);

        double rhoDistance = Math.pow(Math.pow((cspDensity - rho_Density), 2)
                + Math.pow((cspTightness - rho_Tightness), 2), .5);

        double[] distances = {mfdDistance, bbzDistance, ensDistance, mxcDistance,
            fbzDistance, kappaDistance, rhoDistance};

        int bestHeuristic = 0;
        double bestHeuristicDistance = mfdDistance;

        for (int i = 1; i < distances.length; i++) {
            if (distances[i] < bestHeuristicDistance) {
                bestHeuristicDistance = distances[i];
                bestHeuristic = i;
            }
        }
        
        switch (bestHeuristic) {
            case 0:
                return VariableOrderingHeuristics.MFD;
            case 1:
                return VariableOrderingHeuristics.BBZ;
            case 2:
                return VariableOrderingHeuristics.ENS;
            case 3:
                return VariableOrderingHeuristics.MXC;
            case 5:
                return VariableOrderingHeuristics.FBZ;
            case 7:
                return VariableOrderingHeuristics.K;
            case 8:
                return VariableOrderingHeuristics.RHO;
        }
        
        return -1;
    }

    public static Double f0() {
        return 0.000000;
    }

    public static Double f1() {
        return 0.010000;
    }

    public static Double f2() {
        return 0.020000;
    }

    public static Double f3() {
        return 0.030000;
    }

    public static Double f4() {
        return 0.040000;
    }

    public static Double f5() {
        return 0.050000;
    }

    public static Double f6() {
        return 0.060000;
    }

    public static Double f7() {
        return 0.070000;
    }

    public static Double f8() {
        return 0.080000;
    }

    public static Double f9() {
        return 0.090000;
    }

    public static Double f10() {
        return 0.100000;
    }

    public static Double f11() {
        return 0.110000;
    }

    public static Double f12() {
        return 0.120000;
    }

    public static Double f13() {
        return 0.130000;
    }

    public static Double f14() {
        return 0.140000;
    }

    public static Double f15() {
        return 0.150000;
    }

    public static Double f16() {
        return 0.160000;
    }

    public static Double f17() {
        return 0.170000;
    }

    public static Double f18() {
        return 0.180000;
    }

    public static Double f19() {
        return 0.190000;
    }

    public static Double f20() {
        return 0.200000;
    }

    public static Double f21() {
        return 0.210000;
    }

    public static Double f22() {
        return 0.220000;
    }

    public static Double f23() {
        return 0.230000;
    }

    public static Double f24() {
        return 0.240000;
    }

    public static Double f25() {
        return 0.250000;
    }

    public static Double f26() {
        return 0.260000;
    }

    public static Double f27() {
        return 0.270000;
    }

    public static Double f28() {
        return 0.280000;
    }

    public static Double f29() {
        return 0.290000;
    }

    public static Double f30() {
        return 0.300000;
    }

    public static Double f31() {
        return 0.310000;
    }

    public static Double f32() {
        return 0.320000;
    }

    public static Double f33() {
        return 0.330000;
    }

    public static Double f34() {
        return 0.340000;
    }

    public static Double f35() {
        return 0.350000;
    }

    public static Double f36() {
        return 0.360000;
    }

    public static Double f37() {
        return 0.370000;
    }

    public static Double f38() {
        return 0.380000;
    }

    public static Double f39() {
        return 0.390000;
    }

    public static Double f40() {
        return 0.400000;
    }

    public static Double f41() {
        return 0.410000;
    }

    public static Double f42() {
        return 0.420000;
    }

    public static Double f43() {
        return 0.430000;
    }

    public static Double f44() {
        return 0.440000;
    }

    public static Double f45() {
        return 0.450000;
    }

    public static Double f46() {
        return 0.460000;
    }

    public static Double f47() {
        return 0.470000;
    }

    public static Double f48() {
        return 0.480000;
    }

    public static Double f49() {
        return 0.490000;
    }

    public static Double f50() {
        return 0.500000;
    }

    public static Double f51() {
        return 0.510000;
    }

    public static Double f52() {
        return 0.520000;
    }

    public static Double f53() {
        return 0.530000;
    }

    public static Double f54() {
        return 0.540000;
    }

    public static Double f55() {
        return 0.550000;
    }

    public static Double f56() {
        return 0.560000;
    }

    public static Double f57() {
        return 0.570000;
    }

    public static Double f58() {
        return 0.580000;
    }

    public static Double f59() {
        return 0.590000;
    }

    public static Double f60() {
        return 0.600000;
    }

    public static Double f61() {
        return 0.610000;
    }

    public static Double f62() {
        return 0.620000;
    }

    public static Double f63() {
        return 0.630000;
    }

    public static Double f64() {
        return 0.640000;
    }

    public static Double f65() {
        return 0.650000;
    }

    public static Double f66() {
        return 0.660000;
    }

    public static Double f67() {
        return 0.670000;
    }

    public static Double f68() {
        return 0.680000;
    }

    public static Double f69() {
        return 0.690000;
    }

    public static Double f70() {
        return 0.700000;
    }

    public static Double f71() {
        return 0.710000;
    }

    public static Double f72() {
        return 0.720000;
    }

    public static Double f73() {
        return 0.730000;
    }

    public static Double f74() {
        return 0.740000;
    }

    public static Double f75() {
        return 0.750000;
    }

    public static Double f76() {
        return 0.760000;
    }

    public static Double f77() {
        return 0.770000;
    }

    public static Double f78() {
        return 0.780000;
    }

    public static Double f79() {
        return 0.790000;
    }

    public static Double f80() {
        return 0.800000;
    }

    public static Double f81() {
        return 0.810000;
    }

    public static Double f82() {
        return 0.820000;
    }

    public static Double f83() {
        return 0.830000;
    }

    public static Double f84() {
        return 0.840000;
    }

    public static Double f85() {
        return 0.850000;
    }

    public static Double f86() {
        return 0.860000;
    }

    public static Double f87() {
        return 0.870000;
    }

    public static Double f88() {
        return 0.880000;
    }

    public static Double f89() {
        return 0.890000;
    }

    public static Double f90() {
        return 0.900000;
    }

    public static Double f91() {
        return 0.910000;
    }

    public static Double f92() {
        return 0.920000;
    }

    public static Double f93() {
        return 0.930000;
    }

    public static Double f94() {
        return 0.940000;
    }

    public static Double f95() {
        return 0.950000;
    }

    public static Double f96() {
        return 0.960000;
    }

    public static Double f97() {
        return 0.970000;
    }

    public static Double f98() {
        return 0.980000;
    }

    public static Double f99() {
        return 0.990000;
    }
}
