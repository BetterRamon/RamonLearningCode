#include<stdio.h>
#include<stdlib.h>
#define MaxVertices 100 //�������100������
#define MaxWeight 32767 //���ڽ�ʱΪ32767�������ʱ�� "��"
typedef struct{ //����Ȩ���ڽӾ���ĵĶ���
    int Vertices[MaxVertices];  //������Ϣ������
    int Edge[MaxVertices][MaxVertices]; //�ߵ�Ȩ��Ϣ������
    int numV; //��ǰ�Ķ�����
    int numE; //��ǰ�ı���
}AdjMatrix;

//�����Ȩͼ���ڽӾ����ʾ
void CreateGraph(AdjMatrix *G) //ͼ�����ɺ���
{
    int n,e,vi,vj,w,i,j;
    printf("������ͼ�Ķ������ͱ������Կո�ָ�����");
    scanf("%d%d",&n,&e);
    G->numV=n;G->numE=e;
    for(i=0;i<n;i++) //ͼ�ĳ�ʼ��
        for(j=0;j<n;j++)
            {
            if(i==j)
                G->Edge[i][j]=0;
            else
                G->Edge[i][j]=32767;
            }
        for(i=0;i<G->numV;i++) //���������������
        {
            printf("�������%d���������Ϣ(����):",i+1);
            scanf("%d",&G->Vertices[i]);
        }
    printf("\n");

    for(i=0;i<G->numE;i++)
    {
        printf("������ߵ���Ϣi,j,w(�Կո�ָ�):");
        scanf("%d%d%d",&vi,&vj,&w);
        //��Ϊ����Ȩֵ��ͼ����w����1
        //��Ϊ��Ȩֵ��ͼ����w�����ӦȨֵ

        G->Edge[vi-1][vj-1]=w;//��
        G->Edge[vj-1][vi-1]=w;//��
        //����ͼ���жԳ��ԵĹ��ɣ�ͨ���٢�ʵ��
        //����ͼ���߱������ʣ�����ֻ��Ҫ��
    }
}
void DispGraph(AdjMatrix G) //����ڽӾ������Ϣ
{
    int i,j;
    printf("\n����������Ϣ�����ͣ�:\n");
    for(i=0;i<G.numV;i++)
        printf("%8d",G.Vertices[i]);

    printf("\n����ڽӾ���:\n");
    printf("\t");
    for(i=0;i<G.numV;i++)
        printf("%8d",G.Vertices[i]);

    for(i=0;i<G.numV;i++)
    {
        printf("\n%8d",G.Vertices[i]);
        for(j=0;j<G.numV;j++)
        {
        if(G.Edge[i][j]==32767)
        //����֮��������ʱȨֵΪĬ�ϵ�32767��������ͼ��һ����"0"��ʾ��������ͼ��һ����"��",����Ϊ�˷���ͳһ��� "��"
            printf("%8s", "��");
        else
            printf("%8d",G.Edge[i][j]);
        }
        printf("\n");
    }
}
int main()
{
    AdjMatrix G;
    CreateGraph(&G);
    DispGraph(G);
    return 0;
}