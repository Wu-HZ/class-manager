    // src/utils/request.d.ts

    // 导入 Axios 库中我们需要的类型
    import type { AxiosInstance} from 'axios';

    // 声明 request.js 默认导出的内容是一个 AxiosInstance
    declare const request: AxiosInstance;

    // 导出这个默认值
    export default request;

    // 如果你的 request.js 中有其他命名导出，也需要在这里声明
    // 例如：
    // export const anotherFunction: () => void;
    